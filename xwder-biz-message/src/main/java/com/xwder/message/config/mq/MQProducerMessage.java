package com.xwder.message.config.mq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * 生产者
 */
@Component
@Slf4j
public class MQProducerMessage implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnCallback {

    private RabbitTemplate rabbitTemplate;

    @Autowired
    public MQProducerMessage(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        //rabbitTemplate如果为单例的话，那回调就是最后设置的内容
        rabbitTemplate.setConfirmCallback(this::confirm);
        rabbitTemplate.setReturnCallback(this::returnedMessage);
        rabbitTemplate.setMandatory(true);
    }

    /**
     * 发送消息
     *
     * @param content
     * @param exchage
     * @param routeKey
     */
    public void sendMsg(Object content, String exchage, String routeKey) {
        CorrelationData correlationId = new CorrelationData(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend(exchage, routeKey, content, correlationId);

    }

    /**
     * 消息发送到队列中，进行消息确认
     *
     * @param correlationData
     * @param ack
     * @param cause
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        log.info("消息确认的id： " + correlationData);
        if (ack) {
            log.info("消息发送成功");
            //发送成功 删除本地数据库存的消息
        } else {
            log.info("消息发送失败：id " + correlationData + "消息发送失败的原因" + cause);
            // 根据本地消息的状态为失败，可以用定时任务去处理数据
        }
    }

    /**
     * 消息发送失败返回监控
     *
     * @param message
     * @param replyCode
     * @param replyText
     * @param exchange
     * @param routingKey
     */
    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        log.info("returnedMessage [消息从交换机到队列失败] replyCode:[{}],replyText:[{}],exchange:[{}],routingKey:[{}],message:[{}]",
                replyCode, replyText, exchange, routingKey, message
        );

    }
}

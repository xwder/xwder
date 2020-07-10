package com.xwder.massge.module.alertover.mq;

import cn.hutool.json.JSONUtil;
import com.rabbitmq.client.Channel;
import com.xwder.cloud.commmon.constan.MailTypeConstant;
import com.xwder.massge.common.MqConstant;
import com.xwder.massge.module.alertover.service.inft.AlertOverMessageServie;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * rabbitmq 消费者
 *
 * @Author: xwder
 * @Date: 2020/1/14 01:15
 * @Description:
 */
@Slf4j
@Component
public class AlertOverMessageReceiver {

    @Autowired
    private AlertOverMessageServie alertOverMessageServie;

    @RabbitListener(queues = MqConstant.XWDER_QUEUE_ALERTOVER)
    public void handleMessage(Message message, Channel channel) throws IOException {
        try {
            String json = new String(message.getBody());
            Map map = JSONUtil.toBean(json, HashMap.class);
            log.info("alertover通知服务 消费者收到消息: {}", map.toString());
            String title = (String) map.get("title");
            String content = (String) map.get("content");
            alertOverMessageServie.sendStrMessge(title, content);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            log.error("消费消息失败了 {} error：" + message.getBody());
            log.error("OrderConsumer  handleMessage {} , error:", message, e);
            // 处理消息失败，将消息重新放回队列
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
        }
    }
}

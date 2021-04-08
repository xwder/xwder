package com.xwder.message.module.mail.mq;

import cn.hutool.json.JSONUtil;
import com.rabbitmq.client.Channel;
import com.xwder.cloud.commmon.constan.MailTypeConstant;
import com.xwder.message.common.MqConstant;
import com.xwder.message.module.mail.service.MailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
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
public class EmailMessageReceiver {

    @Autowired
    private MailService mailService;

    @RabbitListener(queues = MqConstant.XWDER_QUEUE_EMAIL)
    public void handleMessage(Message message, Channel channel) throws IOException {
        try {
            String json = new String(message.getBody());
            Map map = JSONUtil.toBean(json, HashMap.class);
            String mailType = (String) map.get("mailType");
            log.info("[{}] 类型邮件推送服务 消费者收到消息  : {}", mailType, map.toString());
            String to = (String) map.get("to");
            String subject = (String) map.get("subject");
            String content = (String) map.get("content");
            String filePath = (String) map.get("filePath");
            switch (mailType) {
                case MailTypeConstant.SIMPLEMAIL:
                    mailService.sendSimpleMail(to, subject, content);
                    break;
                case MailTypeConstant.HTMLMAIL:
                    mailService.sendHtmlMail(to, subject, content);
                    break;
                case MailTypeConstant.ATTACHMENTSMAIL:
                    mailService.sendAttachmentsMail(to, subject, content, filePath);
                default:
                    mailService.sendSimpleMail(to, subject, content);
            }
            /**
             * 防止重复消费，可以根据传过来的唯一ID先判断缓存数据中是否有数据
             * 1、有数据则不消费，直接应答处理
             * 2、缓存没有数据，则进行消费处理数据，处理完后手动应答
             * 3、如果消息 处理异常则，可以存入数据库中，手动处理（可以增加短信和邮件提醒功能）
             */
            //手动应答
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            log.error("消费消息失败了 {} error：" + message.getBody());
            log.error("OrderConsumer  handleMessage {} , error:", message, e);
            // 处理消息失败，将消息重新放回队列
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
        }
    }
}

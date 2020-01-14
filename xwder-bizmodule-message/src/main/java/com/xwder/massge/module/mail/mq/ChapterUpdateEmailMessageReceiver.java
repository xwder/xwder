package com.xwder.massge.module.mail.mq;

import com.xwder.massge.module.mail.service.MailService;
import com.xwder.massge.module.mq.config.RabbitConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * rabbitmq 消费者 接受书籍更新邮件推送
 *
 * @Author: xwder
 * @Date: 2020/1/14 01:15
 * @Description:
 */
@Slf4j
@Component
@RabbitListener(queues = RabbitConfig.XWDER_EMAIL_QUEUE_BOOK_UPDATE)
public class ChapterUpdateEmailMessageReceiver {

    @Autowired
    private MailService mailService;

    @RabbitHandler
    public void process(Map map) {
        log.info("邮件推送服务 消费者收到消息  : {}", map.toString());
        String to = (String) map.get("to");
        String subject = (String) map.get("subject");
        String content = (String) map.get("content");
        mailService.sendSimpleMail(to, subject, content);
    }
}

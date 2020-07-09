package com.xwder.massge.module.mail.mq;

import com.xwder.cloud.commmon.constan.MailTypeConstant;
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
        String mailType = (String) map.get("mailType");
        log.info("[{}] 类型邮件推送服务 消费者收到消息  : {}", mailType, map.toString());
        String to = (String) map.get("to");
        String subject = (String) map.get("subject");
        String content = (String) map.get("content");
        switch (mailType) {
            case MailTypeConstant
                    .SIMPLEMAIL:
                mailService.sendSimpleMail(to, subject, content);
                break;
            case MailTypeConstant.HTMLMAIL:
                mailService.sendHtmlMail(to, subject, content);
                break;

            default:
                mailService.sendSimpleMail(to, subject, content);
        }
    }
}

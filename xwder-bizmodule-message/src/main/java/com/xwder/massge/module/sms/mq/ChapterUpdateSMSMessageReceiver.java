package com.xwder.massge.module.sms.mq;

import com.xwder.massge.module.mail.service.MailService;
import com.xwder.massge.module.mq.config.RabbitConfig;
import com.xwder.massge.module.sms.service.QCloudSMSService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * rabbitmq 消费者 接受书籍更新短信推送
 *
 * @Author: xwder
 * @Date: 2020/1/14 01:15
 * @Description:
 */
@Slf4j
@Component
@RabbitListener(queues = RabbitConfig.XWDER_SMS_QUEUE_BOOK_UPDATE)
public class ChapterUpdateSMSMessageReceiver {

    @Autowired
    private QCloudSMSService qCloudSMSService;

    @RabbitHandler
    public void process(Map map) {
        log.info("短信推送服务 消费者收到消息  : {}", map.toString());
        String phone = (String) map.get("phone");
        String content = (String) map.get("content");
        qCloudSMSService.sendSMS(phone,content);
    }
}

package com.xwder.massge.module.mq;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author wande
 * @version 1.0
 * @date 2020/01/13
 */
@Component
@RabbitListener(queues = "sms.bookUpdate")
public class BookUpdateRabbitReceiver {

    @RabbitHandler
    public void process(Map testMessage) {
        System.out.println("BookUpdateRabbitReceiver 消费者收到消息  : " + testMessage.toString());
    }

}

package com.xwder.message.mq;

import com.xwder.message.XwderMessageApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author wande
 * @version 1.0
 * @date 2020/01/13
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = XwderMessageApplication.class)
public class RabbitMqTest {

    //使用RabbitTemplate,这提供了接收/发送等等方法
    @Autowired
    RabbitTemplate rabbitTemplate;

    @Test
    public void TestRabbitMqTopicMessage() {
    }

}

package com.xwder.manage.message;

import com.xwder.manage.XwderManageApplication;
import com.xwder.manage.modules.mq.config.RabbitConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author wande
 * @version 1.0
 * @date 2020/01/13
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = XwderManageApplication.class)
public class RabbitMqTest {

    //使用RabbitTemplate,这提供了接收/发送等等方法
    @Autowired
    RabbitTemplate rabbitTemplate;

    @Test
    public void TestRabbitMqTopicMessage() {
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "message: M A N ";
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String, Object> manMap = new HashMap<>();
        manMap.put("messageId", messageId);
        manMap.put("messageData", messageData);
        manMap.put("createTime", createTime);
        rabbitTemplate.convertAndSend(RabbitConfig.XWDER_EXCHANGE_BOOK, RabbitConfig.XWDER_SMS_QUEUE_BOOK_UPDATE, manMap);
    }

}

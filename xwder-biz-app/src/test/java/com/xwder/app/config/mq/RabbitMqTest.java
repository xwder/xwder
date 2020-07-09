package com.xwder.app.config.mq;

import com.google.common.collect.Maps;
import com.xwder.app.config.mq.RabbitConfig;
import com.xwder.app.XwderApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

/**
 * @author wande
 * @version 1.0
 * @date 2020/01/13
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = XwderApplication.class)
public class RabbitMqTest {

    //使用RabbitTemplate,这提供了接收/发送等等方法
    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    private RabbitConfig rabbitConfig;

    @Test
    public void TestRabbitMqTopicMessage() {
        // 发送邮件
        Map mailMap = Maps.newHashMap();
        mailMap.put("to", "1123511540@qq.com");
        mailMap.put("subject", "章节更新");
        mailMap.put("content", "章节更新");
        //rabbitTemplate.convertAndSend(rabbitConfig.getXwderExchageBook(), rabbitConfig.getXwderQueueEmailChapterUpdate(), mailMap);

        Map map = Maps.newHashMap();
        map.put("title", "test rabbitmq");
        map.put("content", "test rabbitmq");
        rabbitTemplate.convertAndSend(rabbitConfig.getXwderExchageBook(), rabbitConfig.getXwderQueueAlertOverChapterUpdate(), mailMap);
    }

}

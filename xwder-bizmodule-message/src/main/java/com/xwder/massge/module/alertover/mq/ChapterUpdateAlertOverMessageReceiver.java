package com.xwder.massge.module.alertover.mq;

import com.xwder.massge.module.alertover.service.inft.AlertOverMessageServie;
import com.xwder.massge.module.mq.config.RabbitConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * rabbitmq 消费者 接受书籍更新alertover消息推送
 *
 * @Author: xwder
 * @Date: 2020/1/14 01:15
 * @Description:
 */
@Slf4j
@Component
@RabbitListener(queues = RabbitConfig.XWDER_ALERTOVER_QUEUE_BOOK_UPDATE)
public class ChapterUpdateAlertOverMessageReceiver {

    @Autowired
    private AlertOverMessageServie alertOverMessageServie;

    @RabbitHandler
    public void process(Map map) {
        log.info("alertover通知服务 消费者收到消息: {}", map.toString());
        String title = (String) map.get("title");
        String content = (String) map.get("content");
        alertOverMessageServie.sendStrMessge(title, content);
    }
}

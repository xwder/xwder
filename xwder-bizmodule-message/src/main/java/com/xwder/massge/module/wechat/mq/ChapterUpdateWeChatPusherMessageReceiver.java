package com.xwder.massge.module.wechat.mq;

import com.xwder.massge.module.mq.config.RabbitConfig;
import com.xwder.massge.module.wechat.service.intf.WxPusherWeChatMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * rabbitmq 消费者 接受书籍更新微信推送
 *
 * @Author: xwder
 * @Date: 2020/1/14 01:15
 * @Description:
 */
@Slf4j
@Component
@RabbitListener(queues = RabbitConfig.XWDER_WECHAT_QUEUE_BOOK_UPDATE)
public class ChapterUpdateWeChatPusherMessageReceiver {

    @Autowired
    private WxPusherWeChatMessageService wxPusherWeChatMessageService;

    @RabbitHandler
    public void process(Map map) {
        log.info("微信wxpusher推送服务 消费者收到消息  : {}", map.toString());
        String uid = (String) map.get("uid");
        String msg = (String) map.get("msg");
        wxPusherWeChatMessageService.sendStrMessage(uid, msg);
    }
}

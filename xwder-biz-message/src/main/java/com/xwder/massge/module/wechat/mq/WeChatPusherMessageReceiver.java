package com.xwder.massge.module.wechat.mq;

import cn.hutool.json.JSONUtil;
import com.rabbitmq.client.Channel;
import com.xwder.massge.common.MqConstant;
import com.xwder.massge.module.wechat.service.intf.WxPusherWeChatMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
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
public class WeChatPusherMessageReceiver {

    @Autowired
    private WxPusherWeChatMessageService wxPusherWeChatMessageService;

    @RabbitListener(queues = MqConstant.XWDER_QUEUE_WECHAT)
    public void handleMessage(Message message, Channel channel) throws IOException {
        try {
            String json = new String(message.getBody());
            Map map = JSONUtil.toBean(json, HashMap.class);
            log.info("微信wxpusher推送服务 消费者收到消息  : {}", map.toString());
            String uid = (String) map.get("uid");
            String msg = (String) map.get("msg");
            wxPusherWeChatMessageService.sendStrMessage(uid, msg);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            log.error("消费消息失败了 {} error：" + message.getBody());
            log.error("OrderConsumer  handleMessage {} , error:", message, e);
            // 处理消息失败，将消息重新放回队列
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
        }
    }
}

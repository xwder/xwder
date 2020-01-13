package com.xwder.massge.module.mail.mq;

import com.xwder.massge.module.mail.service.MailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: xwder
 * @Date: 2020/1/14 01:15
 * @Description:
 */
@Slf4j
@Component
@RabbitListener(queues = "xwder.queue.email.chapterUpdate")
public class BookUpdateRabbitReceiver {

    @Autowired
    private MailService mailService;

    @RabbitHandler
    public void process(Map map) {
        log.info("BookUpdateRabbitReceiver 消费者收到消息  : {}", map.toString());
        String msg = map.toString();
        //可以点进Message里面看源码,单引号直接的数据就是我们的map消息数据
        String[] msgArray = msg.split("'");
        Map<String, String> msgMap = mapStringToMap(msgArray[1].trim());
        String to=msgMap.get("to");
        String subject=msgMap.get("subject");
        String content=msgMap.get("content");
        mailService.sendSimpleMail(to,subject,content);
    }

    /**
     * {key=value,key=value,key=value} 格式转换成map
     * @param str
     * @return
     */
    private Map<String, String> mapStringToMap(String str) {
        str = str.substring(1, str.length() - 1);
        String[] strs = str.split(",");
        Map<String, String> map = new HashMap<String, String>();
        for (String string : strs) {
            String key = string.split("=")[0].trim();
            String value = string.split("=")[1];
            map.put(key, value);
        }
        return map;
    }


}

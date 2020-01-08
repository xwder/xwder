package com.xwder.manage.modules.message.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.xwder.manage.modules.message.config.MessageConfig;
import com.xwder.manage.modules.message.service.intl.MailService;
import com.xwder.manage.utils.HttpClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @Author: xwder
 * @Date: 2020/1/8 22:53
 * @Description:
 */
@Service
public class MailServiceImpl implements MailService {

    @Autowired
    private MessageConfig messageConfig;

    @Override
    public boolean sendSimpleMail(String to, String subject, String content) {
        Map map = Maps.newHashMap();
        map.put("to", to);
        map.put("subject", subject);
        map.put("content", content);
        String url = messageConfig.getGatewayUrl() + messageConfig.getMailUrl() + "?to=" + to + "&subject=" + subject + "&content=" + content;
        String result = HttpClientUtil.doGet(url);
        Map resultMap = JSON.parseObject(result, Map.class);
        int code = (int) resultMap.get("code");
        return code == 200;
    }

    @Override
    public boolean sendHtmlMail(String to, String subject, String content) {
        Map map = Maps.newHashMap();
        map.put("to", to);
        map.put("subject", subject);
        map.put("content", content);
        String result = HttpClientUtil.doPost(messageConfig.getGatewayUrl() + messageConfig.getHtmlMailUrl(), map);
        Map resultMap = JSON.parseObject(result, Map.class);
        int code = (int) resultMap.get("code");
        return code == 200;
    }
}

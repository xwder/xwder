package com.xwder.manage.modules.message.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.xwder.manage.modules.message.config.MessageConfig;
import com.xwder.manage.modules.message.service.intl.MailService;
import com.xwder.manage.utils.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

/**
 * @Author: xwder
 * @Date: 2020/1/8 22:53
 * @Description:
 */
@Slf4j
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
        String url = messageConfig.getGatewayUrl() + messageConfig.getMailUrl();
        String result = null;
        try {
            result = HttpClientUtil.doPost(url, map);
        } catch (IOException e) {
            log.error("发送邮件失败，错误信息[{}]", e);
            return false;
        }
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
        String result = null;
        try {
            result = HttpClientUtil.doPost(messageConfig.getGatewayUrl() + messageConfig.getHtmlMailUrl(), map);
        } catch (IOException e) {
            log.error("发送html邮件失败，错误信息[{}]", e);
            return false;
        }
        Map resultMap = JSON.parseObject(result, Map.class);
        int code = (int) resultMap.get("code");
        return code == 200;
    }
}

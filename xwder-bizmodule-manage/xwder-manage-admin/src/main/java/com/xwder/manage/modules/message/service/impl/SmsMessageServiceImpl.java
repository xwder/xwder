package com.xwder.manage.modules.message.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.xwder.manage.modules.message.config.MessageConfig;
import com.xwder.manage.modules.message.service.intl.SmsMessageService;
import com.xwder.manage.utils.HttpClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

/**
 * @author wande
 * @version 1.0
 * @date 2020/01/08
 */
@Service
public class SmsMessageServiceImpl implements SmsMessageService {

    @Autowired
    private MessageConfig messageConfig;

    @Override
    public boolean sendSMS(String phone, String content) throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException {
        String url = messageConfig.getGatewayUrl() + messageConfig.qSMSUrl;
        Map paramMap = Maps.newHashMap();
        paramMap.put("phone", phone);
        paramMap.put("content", content);
        String result = HttpClientUtil.doGet(url, paramMap);
        Map map = JSON.parseObject(result, Map.class);
        int code = (int) map.get("code");
        return code == 200;
    }
}

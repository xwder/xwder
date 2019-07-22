package com.xwder.massge.sms.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xwder.framework.utils.date.DateUtils;
import com.xwder.framework.utils.message.Result;
import com.xwder.framework.utils.request.HttpClientUtil;
import com.xwder.massge.sms.config.SmsMessageConfig;
import com.xwder.massge.sms.service.SmsMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @Author: xwder
 * @Date: 2019/7/20 00:12
 * @Description:
 */
@Service
public class SmsMessageServiceImpl implements SmsMessageService {

    @Autowired
    private SmsMessageConfig smsMessageConfig;

    @Override
    public Result sendSmsMessage(String phone, String content) {

        Map<String,Object> paramMap = new HashMap<>(16);
        paramMap.put("account",smsMessageConfig.getAccount());
        paramMap.put("password",smsMessageConfig.getPassword());
        paramMap.put("ukey", UUID.randomUUID().toString().replaceAll("-",""));

        Map<String,String> dataMap = new HashMap<>(16);
        dataMap.put("phones",phone);
        dataMap.put("content",content);
        dataMap.put("sign",smsMessageConfig.getSign());
        dataMap.put("extend",smsMessageConfig.getExtend());
        dataMap.put("sendtime", "");
        paramMap.put("data",dataMap);

        String paramJson = JSON.toJSONString(paramMap);

        String result = HttpClientUtil.doPostJson(smsMessageConfig.getUrl(), paramJson);

        System.out.println(result);
        return null;
    }
}

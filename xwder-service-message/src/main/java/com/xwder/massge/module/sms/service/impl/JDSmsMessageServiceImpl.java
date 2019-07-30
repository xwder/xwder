package com.xwder.massge.module.sms.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.xwder.framework.utils.message.Result;
import com.xwder.framework.utils.message.ResultUtil;
import com.xwder.framework.utils.request.HttpClientUtil;
import com.xwder.massge.module.sms.config.JDSmsMessageConfig;
import com.xwder.massge.module.sms.service.JDSmsMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @Author: xwder
 * @Date: 2019/7/20 00:12
 * @Description:
 */
@Service
public class JDSmsMessageServiceImpl implements JDSmsMessageService {

    @Autowired
    private JDSmsMessageConfig smsMessageConfig;

    @HystrixCommand(fallbackMethod = "sendJDSmsMessageFallback")
    @Override
    public Result sendJDSmsMessage(String phone, String content) {

        Map<String, Object> paramMap = new HashMap<>(16);
        paramMap.put("account", smsMessageConfig.getAccount());
        paramMap.put("password", smsMessageConfig.getPassword());
        paramMap.put("ukey", UUID.randomUUID().toString().replaceAll("-", ""));

        Map<String, String> dataMap = new HashMap<>(16);
        dataMap.put("phones", phone);
        dataMap.put("content", content);
        dataMap.put("sign", smsMessageConfig.getSign());
        dataMap.put("extend", smsMessageConfig.getExtend());
        dataMap.put("sendtime", "");
        paramMap.put("data", dataMap);

        String paramJson = JSON.toJSONString(paramMap);

        String result = HttpClientUtil.doPostJson(smsMessageConfig.getUrl(), paramJson);

        JSONObject jsonObject = JSONObject.parseObject(result);
        Integer errorCode = jsonObject.getInteger("error_code");

        if (errorCode == 0) {
            return ResultUtil.success("发送成功！");
        } else {
            return ResultUtil.error(jsonObject.get("error_msg").toString());
        }
    }


    /**
     * 服务不可用 降级回调方法
     * @return
     */
    public Result sendJDSmsMessageFallback(String phone, String content) {
        return ResultUtil.error("发送失败");
    }
}

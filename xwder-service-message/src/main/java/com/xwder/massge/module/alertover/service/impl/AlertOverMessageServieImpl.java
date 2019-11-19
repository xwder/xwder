package com.xwder.massge.module.alertover.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.xwder.framework.utils.message.Result;
import com.xwder.framework.utils.message.ResultUtil;
import com.xwder.framework.utils.request.HttpClientUtil;
import com.xwder.massge.module.alertover.config.AlertOverConfig;
import com.xwder.massge.module.alertover.service.inft.AlertOverMessageServie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * 发送alertover app消息推送
 *
 * @author wande
 * @version 1.0
 * @date 2019/11/19
 */
@Service
public class AlertOverMessageServieImpl implements AlertOverMessageServie {

    @Autowired
    private AlertOverConfig alertOverConfig;

    @Override
    public Result sendStrMessgeWithSource(String sendSource, String receiveSource, String title, String content) {

        HashMap<String, String> paramMap = Maps.newHashMap();
        paramMap.put("source", sendSource);
        paramMap.put("receiver", receiveSource);
        paramMap.put("title", title);
        paramMap.put("content", content);

        String result = HttpClientUtil.doPost(alertOverConfig.getRequestUrl(), paramMap);
        JSONObject jsonObject = JSONObject.parseObject(result);
        int code = Math.toIntExact(jsonObject.getLong("code"));
        String msg = (String) jsonObject.get("msg");

        return Result.builder().code(code).msg(msg).build();
    }

    @Override
    public Result sendStrMessge(String title, String content) {
        return sendStrMessgeWithSource(alertOverConfig.getSendSource(), alertOverConfig.getReceiveSource(), title, content);
    }
}

package com.xwder.massge.module.alertover.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.xwder.framework.utils.message.Result;
import com.xwder.framework.utils.message.ResultUtil;
import com.xwder.framework.utils.request.HttpClientUtil;
import com.xwder.massge.module.alertover.config.AlertOverConfig;
import com.xwder.massge.module.alertover.service.inft.AlertOverMessageServie;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class AlertOverMessageServieImpl implements AlertOverMessageServie {

    @Autowired
    private AlertOverConfig alertOverConfig;

    @Override
    public Result sendStrMessgeWithSource(String sendSource, String receiveSource, String title, String content) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("source", sendSource);
        jsonObject.put("receiver", receiveSource);
        jsonObject.put("title", title);
        jsonObject.put("content", content);

        String result = HttpClientUtil.doPostJson(alertOverConfig.getRequestUrl(), jsonObject.toString());
        JSONObject resultJsonObject = JSONObject.parseObject(result);
        int code = Math.toIntExact(resultJsonObject.getLong("code"));
        if(0 == code){
            log.info("alertover{}",resultJsonObject.toString());
        }else {
            log.error("alertover{}",resultJsonObject.toString());
        }
        String msg = (String) resultJsonObject.get("msg");
        return ResultUtil.result(code, msg);
    }

    @Override
    public Result sendStrMessge(String title, String content) {
        return sendStrMessgeWithSource(alertOverConfig.getSendSource(), alertOverConfig.getReceiveSource(), title, content);
    }
}

package com.xwder.message.module.alertover.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.xwder.cloud.commmon.api.CommonResult;
import com.xwder.cloud.commmon.utils.HttpClientUtil;
import com.xwder.message.module.alertover.config.AlertOverConfig;
import com.xwder.message.module.alertover.service.inft.AlertOverMessageServie;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public CommonResult sendStrMessgeWithSource(String sendSource, String receiveSource, String title, String content) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("source", sendSource);
        jsonObject.put("receiver", receiveSource);
        jsonObject.put("title", title);
        jsonObject.put("content", content);

        String result = HttpClientUtil.doPostJson(alertOverConfig.getRequestUrl(), jsonObject.toString());
        JSONObject resultJsonObject = JSONObject.parseObject(result);
        int code = Math.toIntExact(resultJsonObject.getLong("code"));
        if (0 == code) {
            log.info("alertover{}", resultJsonObject.toString());
        } else {
            log.error("alertover{}", resultJsonObject.toString());
        }
        String msg = (String) resultJsonObject.get("msg");
        return CommonResult.commonResult(code, msg, null);
    }

    @Override
    public CommonResult sendStrMessge(String title, String content) {
        return sendStrMessgeWithSource(alertOverConfig.getSendSource(), alertOverConfig.getReceiveSource(), title, content);
    }
}

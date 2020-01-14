package com.xwder.manage.modules.message.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.xwder.manage.modules.message.config.MessageConfig;
import com.xwder.manage.modules.message.service.intl.WeChatMessageService;
import com.xwder.manage.utils.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

/**
 * @Author: xwder
 * @Date: 2020/1/14 23:37
 * @Description:
 */
@Slf4j
@Service
public class WeChatMessageServiceImpl implements WeChatMessageService {

    @Autowired
    private MessageConfig messageConfig;

    @Override
    public boolean sendStrMessage(String uid, String content) {
        String url = messageConfig.getGatewayUrl() + messageConfig.getWxPusher();
        Map paramMap = Maps.newHashMap();
        paramMap.put("uid", "UID_3izJBf0A7BG2CsQ8bdaBjnm4JKho");
        paramMap.put("content", content);
        String result = null;
        try {
            result = HttpClientUtil.doPost(url, paramMap);
        } catch (IOException e) {
            log.error("发送wxpusher通知失败，通知信息[{}],错误信息[{}]", uid + "===" + content, e);
            e.printStackTrace();
        }
        Map map = JSON.parseObject(result, Map.class);
        int code = (int) map.get("code");
        return code == 200;
    }
}

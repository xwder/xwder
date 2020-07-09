package com.xwder.manage.modules.message.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.xwder.manage.modules.message.config.MessageConfig;
import com.xwder.manage.modules.message.service.intl.AlertOverService;
import com.xwder.manage.utils.HttpClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

/**
 * @Author: xwder
 * @Date: 2020/1/8 23:01
 * @Description:
 */
@Service
public class AlertOverServiceImpl implements AlertOverService {

    @Autowired
    private MessageConfig messageConfig;

    @Override
    public boolean sendStrMessge(String title, String content) {
        Map map = Maps.newHashMap();
        map.put("title", title);
        map.put("content", content);
        String url = messageConfig.getGatewayUrl() + messageConfig.getAlertoverUrl();
        String result = null;
        try {
            result = HttpClientUtil.doPost(url, map);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Map resultMap = JSON.parseObject(result, Map.class);
        int code = (int) resultMap.get("code");
        return code == 200;
    }
}

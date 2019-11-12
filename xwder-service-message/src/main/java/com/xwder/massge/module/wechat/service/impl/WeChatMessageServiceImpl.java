package com.xwder.massge.module.wechat.service.impl;

import com.xwder.framework.utils.request.HttpClientUtil;
import com.xwder.massge.module.wechat.config.WeChatMessageConfig;
import com.xwder.massge.module.wechat.service.WeChatMessageService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author wande
 * @version 1.0
 * @date 2019/11/12
 */
public class WeChatMessageServiceImpl implements WeChatMessageService {

    @Autowired
    WeChatMessageConfig weChatMessageConfig;

    @Override
    public void sendStrMessage(String msg) {

    }

    @Override
    public void sendHtmlMessage(String url) {

    }
}

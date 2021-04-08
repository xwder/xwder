package com.xwder.message.module.wechat.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 微信消息推送
 * @author wande
 * @version 1.0
 * @date 2019/11/12
 */
@Data
@Component
public class WeChatMessageConfig {

    /**
     * Server酱 seckey
     */
    @Value("${ftqq.seckey}")
    private String ftqqSeckey;

    /**
     * Server酱 url
     */
    @Value("${ftqq.url}")
    private String ftqqUrl;

    /**
     * wxpusher apptoken
     */
    @Value("${wxpusher.biz.apptoken}")
    private String wxPusherAppToken;

}

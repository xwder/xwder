package com.xwder.massge.module.wechat.config;

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

    @Value("${ftqq.seckey}")
    private String seckey;

    @Value("${ftqq.url}")
    private String url;

}

package com.xwder.manage.module.message.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Author: xwder
 * @Date: 2019/11/13 00:24
 * @Description:
 */
@Data
@Component
public class MessageConfig {

    /**
     * wxpusher uid
     */
    @Value("${wxpusher.biz.uid}")
    private String uid;

    /**
     * wxpusher apptoken
     */
    @Value("${wxpusher.biz.apptoken}")
    private String wxPusherAppToken;
}

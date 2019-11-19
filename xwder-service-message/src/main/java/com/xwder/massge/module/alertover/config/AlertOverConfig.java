package com.xwder.massge.module.alertover.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * alertover app消息推送
 * @author wande
 * @version 1.0
 * @date 2019/11/19
 */
@Data
@Component
public class AlertOverConfig {
    /**
     * alertover sendSource
     */
    @Value("${alertover.send-source}")
    private String sendSource;

    /**
     * alertover receiveSource
     */
    @Value("${alertover.receive-source}")
    private String receiveSource;

    /**
     * alertover requestUrl
     */
    @Value("${alertover.request-url}")
    private String requestUrl;

}

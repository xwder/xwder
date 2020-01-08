package com.xwder.manage.modules.message.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author wande
 * @version 1.0
 * @date 2020/01/08
 */
@Data
@Component
@ConfigurationProperties(prefix = "messageservice")
public class MessageConfig {

    private String gatewayUrl;

    private String qSMSUrl;

    private String alertoverUrl;

    private String mailUrl;

    private String htmlMailUrl;
}

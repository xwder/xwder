package com.xwder.cloud.config.client.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * @author wande
 * @version 1.0
 * @date 2020/04/27
 */
@Data
@Component
@RefreshScope
@ConfigurationProperties(prefix = "xwder")
public class XwderProperties {

    private String username = "username";

}

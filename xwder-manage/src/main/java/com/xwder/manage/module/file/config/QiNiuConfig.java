package com.xwder.manage.module.file.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author : xwder
 * @version : 1.0
 * @class :
 * @description : the setting key and value for file utils
 * @date : 2019-8-3 00:33:53
 */
@Data
@Component
@ConfigurationProperties(prefix = "xwder-manage.qiniuyun")
public class QiNiuConfig {
    private String accessKey;
    private String secretKey;
    private String bucket;
    private String path;
    private Long imageMaxSize;
    private Long imageMaxNameLength;
}
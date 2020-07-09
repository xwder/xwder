package com.xwder.massge.module.sms.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Author: xwder
 * @Date: 2019/7/25 23:50
 * @Description:
 */
@Component
@Data
public class MyQcloudSMSConfig {

    @Value("${myqcloud-sms.SecretId}")
    private String secretId;
    @Value("${myqcloud-sms.SecretKey}")
    private String secretKey;
    @Value("${myqcloud-sms.url}")
    private String url;

}

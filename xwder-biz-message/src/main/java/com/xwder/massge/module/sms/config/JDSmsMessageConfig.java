package com.xwder.massge.module.sms.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Author: xwder
 * @Date: 2019/7/20 00:14
 * @Description:
 */
@Data
@Component
public class JDSmsMessageConfig {

    @Value("${jdcloud-sms.url}")
    private String url;
    @Value("${jdcloud-sms.account}")
    private String account;
    @Value("${jdcloud-sms.password}")
    private String password;
    @Value("${jdcloud-sms.sign}")
    private String sign;
    @Value("${jdcloud-sms.extend}")
    private String extend;


}

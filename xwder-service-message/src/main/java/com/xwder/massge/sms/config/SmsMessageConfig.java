package com.xwder.massge.sms.config;

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
public class SmsMessageConfig {

    @Value("${sms.url}")
    private String url;
    @Value("${sms.account}")
    private String account;
    @Value("${sms.password}")
    private String password;
    @Value("${sms.sign}")
    private String sign;
    @Value("${sms.extend}")
    private String extend;


}

package com.xwder.cloud.config.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * xwder-cloud-config-client
 * 具有spring-cloud-gateway配置
 *
 * @Author: xwder
 * @Date: 2020/04/27
 * @Description:
 */
@RestController
@EnableEurekaClient
@SpringBootApplication
public class XwderConfigClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(XwderConfigClientApplication.class);
    }

    @Value("${xwder.username}")
    String username;

    @RequestMapping(value = "/config")
    public Object redis() {
        HashMap hashMap = new HashMap();
        hashMap.put("username", username);
        return hashMap;
    }
}

package com.xwder.cloud.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * ConfigServer
 *
 * @Author: xwder
 * @Date: 2019/12/27
 * @Description:
 */
@SpringBootApplication
@EnableConfigServer
@EnableEurekaClient
public class XwderConfigServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(XwderConfigServerApplication.class);
    }

}

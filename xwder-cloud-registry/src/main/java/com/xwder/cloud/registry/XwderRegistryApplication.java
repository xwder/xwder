package com.xwder.cloud.registry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author wande
 * @version 1.0
 * @date 2019/12/25
 */
@SpringBootApplication
@EnableEurekaServer
public class XwderRegistryApplication {

    public static void main(String[] args) {
        SpringApplication.run(XwderRegistryApplication.class, args);
    }

}

package com.xwder.cloud.admin;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Configuration;

/**
 * @author wande
 * @version 1.0
 * @date 2020/07/16
 */
@EnableEurekaClient
@EnableAdminServer
@SpringBootApplication
public class XwderAdminServerApp {

    public static void main(String[] args) {
        SpringApplication.run(XwderAdminServerApp.class);
    }
}

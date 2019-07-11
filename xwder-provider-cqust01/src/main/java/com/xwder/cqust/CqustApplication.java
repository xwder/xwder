package com.xwder.cqust;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Author: xwder
 * @Date: 2019/7/11 23:05
 * @Description:
 */
@SpringBootApplication
@EnableDiscoveryClient
public class CqustApplication {

    public static void main(String[] args) {
        SpringApplication.run(CqustApplication.class, args);
    }

}

package com.xwder.massge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

/**
 * @Author: xwder
 * @Date: 2019/7/13 19:10
 * @Description:
 */
@EnableDiscoveryClient
@SpringBootApplication
@EnableHystrixDashboard
@EnableCircuitBreaker
public class XwderMessageApplication {

    public static void main(String[] args) {
        SpringApplication.run(XwderMessageApplication.class, args);
    }
}


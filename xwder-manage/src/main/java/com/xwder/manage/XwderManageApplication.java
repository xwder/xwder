package com.xwder.manage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * SpringBootApplication 需要把 hystrix 回调的包扫描到
 *
 * @ClassName: XwderManageApplication
 * @Description:
 * @Author: xwder
 * @Date: 2019年7月9日20:55:42
 * @Version: 1.0
 */
@EnableScheduling
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, MongoAutoConfiguration.class},
        scanBasePackages = {"com.xwder.manage", "com.xwder.api"}
)
@EnableFeignClients(basePackages = {"com.xwder.api"})
@EnableCircuitBreaker
@EnableDiscoveryClient
@EnableHystrixDashboard
public class XwderManageApplication {

    public static void main(String[] args) {
        SpringApplication.run(XwderManageApplication.class, args);
    }

}

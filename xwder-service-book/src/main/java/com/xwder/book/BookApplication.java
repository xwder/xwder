package com.xwder.book;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @Author: xwder
 * @Date: 2019/7/15 23:11
 * @Description:
 */

@EnableDiscoveryClient
@SpringBootApplication(exclude = {MongoAutoConfiguration.class})
@MapperScan({"com.xwder.framework.domain.book","com.xwder.book.dao"}) // 扫描的mapper  tk.mybatis
@EnableHystrixDashboard
@EnableCircuitBreaker
public class BookApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookApplication.class, args);
    }

}

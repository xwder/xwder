package com.xwder.cqust;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
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
@SpringBootApplication(exclude = {MongoAutoConfiguration.class, MongoDataAutoConfiguration.class})
@MapperScan({"com.xwder.framework.domain.cqust","com.xwder.cqust.dao"}) // 扫描的mapper  tk.mybatis
@EnableHystrixDashboard
@EnableCircuitBreaker
public class CQUSTApplication {

    public static void main(String[] args) {
        SpringApplication.run(CQUSTApplication.class, args);
    }

}

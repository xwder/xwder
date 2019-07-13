package com.xwder.cqust;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @Author: xwder
 * @Date: 2019/7/11 23:05
 * @Description:
 */
@EnableDiscoveryClient
@SpringBootApplication(exclude = {MongoAutoConfiguration.class, MongoDataAutoConfiguration.class} )
@EnableJpaRepositories(basePackages = "com.xwder.cqust.repository")
@EntityScan(basePackages = "com.xwder.framework.domain.cqust")
public class CqustApplication {

    public static void main(String[] args) {
        SpringApplication.run(CqustApplication.class, args);
    }

}

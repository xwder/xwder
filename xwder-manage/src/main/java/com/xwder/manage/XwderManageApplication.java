package com.xwder.manage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @ClassName: XwderManageApplication
 * @Description:
 * @Author: xwder
 * @Date: 2019年7月9日20:55:42
 * @Version: 1.0
 */
@EnableScheduling
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class,  MongoAutoConfiguration.class})
@EnableFeignClients(basePackages = {"com.xwder.api"})
public class XwderManageApplication {

    public static void main(String[] args) {
        SpringApplication.run(XwderManageApplication.class, args);
    }

}

package com.xwder.cloud.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @Author: xwder
 * @Date: 2019/12/27
 * @Description:
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class XwderCloudGagewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(XwderCloudGagewayApplication.class);
    }

}

package com.ruoyi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 启动程序
 *
 * @author xwder
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class XwderManageApplication {
    public static void main(String[] args) {
        SpringApplication.run(XwderManageApplication.class, args);
    }
}
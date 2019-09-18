package com.xwder.zuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * @Author: xwder
 * @Date: 2019/9/19 00:26
 * @Description:
 */
@EnableZuulProxy
@SpringBootApplication
public class XwderZuulApplication {

    public static void main(String[] args) {
        SpringApplication.run(XwderZuulApplication.class, args);
    }

}

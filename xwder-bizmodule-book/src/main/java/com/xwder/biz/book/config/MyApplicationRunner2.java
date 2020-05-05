package com.xwder.biz.book.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 系统启动完可以做一些业务操作 实现 ApplicationRunner 或者 CommandLineRunner
 * 如果有多个runner需要指定一些顺序
 *
 * @author wande
 * @version 1.0
 * @date 2020/05/05
 */
@Slf4j
@Order(1)
@Component
public class MyApplicationRunner2 implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        log.info("****************==== implements CommandLineRunner  " + args.toString() + "====***********");
    }
}

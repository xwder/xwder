package com.xwder.app.config.async;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * 异步线程配置
 *
 * @author xwder
 */
@Slf4j
@Configuration
public class ThreadPoolExecutorConfig {

    private static final int THREADS = Runtime.getRuntime().availableProcessors() + 1;
    final ThreadFactory threadFactory = new ThreadFactoryBuilder()
            // -%d不要少
            .setNameFormat("async-task-name-%d")
            .setDaemon(true)
            .build();

    @Bean("taskExecutor")
    public Executor taskExecutor() {
        return new ThreadPoolExecutor(THREADS, 2 * THREADS,
                5, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(1024),
                threadFactory, (r, executor) -> {
            log.info("task is rejected!");
        });
    }
}
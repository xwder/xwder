package com.xwder.cloud.gateway.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import reactor.core.publisher.Mono;

/**
 * 全局过滤器使用
 * <p>
 * 通过@Order来指定执行的顺序，数字越小，优先级越高
 *
 * @author xwder
 * @date 2019-9-25 22:52:58
 */
@Configuration
public class GlobalFilterConfiguration {

    private Logger log = LoggerFactory.getLogger(GlobalFilterConfiguration.class);

    @Bean
    @Order(-1)
    public GlobalFilter a() {
        return (exchange, chain) -> {
            log.info("global filter -> first pre filter");
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                log.info("global filter -> third post filter");
            }));
        };
    }

    @Bean
    @Order(0)
    public GlobalFilter b() {
        return (exchange, chain) -> {
            log.info("global filter -> second pre filter");
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                log.info("global filter -> second post filter");
            }));
        };
    }

    @Bean
    @Order(1)
    public GlobalFilter c() {
        return (exchange, chain) -> {
            log.info("global filter -> third pre filter");
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                log.info("global filter -> first post filter");
            }));
        };
    }
}

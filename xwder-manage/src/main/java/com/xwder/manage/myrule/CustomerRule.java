package com.xwder.manage.myrule;

import com.netflix.loadbalancer.IRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 自定义 Ribbon 负载均衡规则
 * @author xwder
 */
@Configuration
public class CustomerRule {
    @Bean
    public IRule myRule() {
        //return new RandomRule();// Ribbon默认是轮询，我自定义为随机
        //return new RoundRobinRule();// Ribbon默认是轮询，我自定义为随机

        // 我自定义为每台机器5次
        return new RandomRule();
    }
}

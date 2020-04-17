package com.xwder.cloud.gateway.config;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * 限流 基于Redis实现
 *
 * @author xwder
 * @date 2019-9-25 22:58:26
 */
@Configuration
public class BeanConfig {
	/**
	 * ip 限流
	 * @return
	 */
    @Bean
    public KeyResolver ipKeyResolver() {
        // 通过KeyResolver来指定限流的Key，比如我们需要根据用户来做限流，或是根据IP来做限流等
        return exchange -> Mono.just(exchange.getRequest().getRemoteAddress().getHostName());
    }

	/**
	 * 用户限流
	 * @return
	 */
/*	@Bean
    public KeyResolver userKeyResolver() {
        return exchange ->
                Mono.just(exchange.getRequest().getQueryParams().getFirst("userId"));
    }*/

	/**
	 * 接口限流
	 * @return
	 */
/*	@Bean
	KeyResolver apiKeyResolver() {
		return exchange -> Mono.just(exchange.getRequest().getPath().value());
	}*/


	/**
     * 获取ip
     *
     * @param request
     * @return
     */
    public static String getIpAddr(ServerHttpRequest request) {
        HttpHeaders headers = request.getHeaders();
        List<String> ips = headers.get("X-Forwarded-For");
        String ip = "192.168.1.1";
        if (ips != null && ips.size() > 0) {
            ip = ips.get(0);
        }
        return ip;
    }

}
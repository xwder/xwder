package com.xwder.cloudgateway.filter.factory;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

/**
 * 自定义Spring Cloud Gateway过滤器工厂需要继承AbstractGatewayFilterFactory类，重写apply方法的逻辑。
 * <p>
 * 命名需要以GatewayFilterFactory结尾，比如CheckAuthGatewayFilterFactory，那么在使用的时候CheckAuth就是这个过滤器工厂的名称。
 * <p>
 * Key、Value这种形式的，那么可以不用自己定义配置类，直接继承AbstractNameValueGatewayFilterFactory类即可
 *
 * @author xwder
 * @date 2019年9月25日22:30:19
 */
@Component
public class CheckAuth2GatewayFilterFactory extends AbstractGatewayFilterFactory<CheckAuth2GatewayFilterFactory.Config> {

    public CheckAuth2GatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            System.err.println("进入了CheckAuth2GatewayFilterFactory" + config.getName());
            ServerHttpRequest request = exchange.getRequest().mutate()
                    .build();

            return chain.filter(exchange.mutate().request(request).build());
        };
    }

    public static class Config {

        private String name;

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

    }
}

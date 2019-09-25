package com.xwder.cloudgateway.filter.factory;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractNameValueGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

/**
 * 自定义GatewayFilterFactory
 *
 * AddRequestHeaderGatewayFilterFactory、AddRequestParameterGatewayFilterFactory等都是直接继承的AbstractNameValueGatewayFilterFactory
 *
 * @author xwder
 * @date 2019-9-25 22:40:35
 */
@Component
public class CheckAuthGatewayFilterFactory extends AbstractNameValueGatewayFilterFactory {

    @Override
    public GatewayFilter apply(NameValueConfig config) {
        return (exchange, chain) -> {
            System.err.println("进入了CheckAuthGatewayFilterFactory" + config.getName() + "\t" + config.getValue());
            ServerHttpRequest request = exchange.getRequest().mutate()
                    .build();

            return chain.filter(exchange.mutate().request(request).build());
        };
    }

}

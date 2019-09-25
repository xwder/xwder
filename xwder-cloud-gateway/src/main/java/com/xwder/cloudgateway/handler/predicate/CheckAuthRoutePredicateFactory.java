package com.xwder.cloudgateway.handler.predicate;

import java.util.function.Predicate;

import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

/**
 * 自定义路由断言工厂需要继承AbstractRoutePredicateFactory类，重写apply方法的逻辑
 * <p><p>
 * apply方法中可以通过exchange.getRequest()拿到ServerHttpRequest对象，从而可以获取到请求的参数、请求方式、请求头等信息
 * <p><p>
 * 命名需要以RoutePredicateFactory结尾，比如CheckAuthRoutePredicateFactory，那么在使用的时候CheckAuth就是这个路由断言工厂的名称
 * <p>
 * @author xwder
 * @date 2019-9-25 22:24:51
 */
@Component
public class CheckAuthRoutePredicateFactory extends AbstractRoutePredicateFactory<CheckAuthRoutePredicateFactory.Config> {

    public CheckAuthRoutePredicateFactory() {
        super(Config.class);
    }

    @Override
    public Predicate<ServerWebExchange> apply(Config config) {
        return exchange -> {
            System.err.println("进入了CheckAuthRoutePredicateFactory\t" + config.getName());
            if (config.getName().equals("xwder")) {
                return true;
            }
            return false;
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

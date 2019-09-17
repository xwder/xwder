package com.xwder.manage.config;

import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;

/**
 * 通过扩展健康检查的端点来模拟异常情况，定义一个扩展端点，将状态设置为DOWN
 *
 * @Author: xwder
 * @Date: 2019/8/26 22:50
 * @Description:
 */
public class CustomHealthIndicator extends AbstractHealthIndicator {

    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
        builder.down().withDetail("status", false);
    }
}

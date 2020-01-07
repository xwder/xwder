package com.xwder.manage.web.modules.book.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author wande
 * @version 1.0
 * @date 2019/12/30
 */
@Component
@ConfigurationProperties(prefix = "service")
@Data
public class BookServiceUrlConfig {

    public String serviceBookGatewayUrl;

    public String listBook;

}

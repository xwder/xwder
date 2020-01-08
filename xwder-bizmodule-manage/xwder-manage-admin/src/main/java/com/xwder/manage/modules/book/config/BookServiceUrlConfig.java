package com.xwder.manage.modules.book.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author wande
 * @version 1.0
 * @date 2019/12/30
 */
@Data
@Component
@ConfigurationProperties(prefix = "bookservice")
public class BookServiceUrlConfig {

    public String gatewayUrl;

    public String listBook;

    public String listChapter;

    public String latestChapters;
}

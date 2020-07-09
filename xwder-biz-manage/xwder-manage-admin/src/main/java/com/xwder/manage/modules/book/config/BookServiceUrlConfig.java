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

    private String gatewayUrl;

    private String listBook;

    private String listChapter;

    private String latestChapters;
}

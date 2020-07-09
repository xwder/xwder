package com.xwder.biz.book.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * spider接口地址
 *
 * @author wande
 * @version 1.0
 * @date 2020/01/08
 */
@Data
@Component
public class BookSpiderUrlConfig {
    /**
     * book latest-chapter-url
     */
    @Value("${book.latest-chapter-url}")
    private String bookLatestChapterUrl;
}

package com.xwder.biz.api.book.fallback;

import com.xwder.biz.api.book.ChapterServiceApi;
import com.xwder.cloud.commmon.api.CommonResult;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 图书模块章节api熔断器
 *
 * @Author: xwder
 * @Date: 2019/7/17 23:15
 * @Description:
 */
@Slf4j
@Component
public class ChapterServiceFallbackFactory implements FallbackFactory<ChapterServiceApi> {
    @Override
    public ChapterServiceApi create(Throwable throwable) {
        return new ChapterServiceApi() {
            @Override
            public CommonResult listChapterByBookId(Integer bookId, Integer withContent, Integer pageNum, Integer pageSize) {
                return CommonResult.failed();
            }

            @Override
            public CommonResult getLatestChapter(String author, String bookName, String bookUrl) {
                return CommonResult.failed();
            }
        };
    }
}

package com.xwder.biz.api.book.fallback;

import com.xwder.biz.api.book.BookInfoServiceApi;
import com.xwder.cloud.commmon.api.CommonResult;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 图书模块书籍api熔断器
 *
 * @Author: xwder
 * @Date: 2019/7/17 23:15
 * @Description:
 */
@Slf4j
@Component
public class BookInfoServiceFallbackFactory implements FallbackFactory<BookInfoServiceApi> {
    @Override
    public BookInfoServiceApi create(Throwable throwable) {
        return new BookInfoServiceApi() {
            @Override
            public CommonResult listBook(Integer pageNum, Integer pageSize) {
                return CommonResult.failed();
            }
        };


    }
}

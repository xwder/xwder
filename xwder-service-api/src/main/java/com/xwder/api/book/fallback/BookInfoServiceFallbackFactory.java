package com.xwder.api.book.fallback;

import com.xwder.api.book.BookInfoServiceApi;
import com.xwder.framework.domain.book.BookChapter;
import com.xwder.framework.domain.book.BookInfo;
import com.xwder.framework.utils.message.Result;
import com.xwder.framework.utils.message.ResultUtil;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

/**
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
            public Result<BookInfo> listBookByPage(Integer page, Integer size, String sortField, Sort.Direction order) {
                log.error(" ================ 调用 listBookByPage  Fallback============");
                return ResultUtil.error(null);
            }

            @Override
            public Result<BookChapter> listBookChapterByPage(Integer bookId,Integer page, Integer size, String sortField, Sort.Direction order) {
                log.error(" ================ 调用 listBookChapterByPage  Fallback============");
                return ResultUtil.error(null);
            }
        };


    }
}

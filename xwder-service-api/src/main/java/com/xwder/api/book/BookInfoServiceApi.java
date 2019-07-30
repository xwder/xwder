package com.xwder.api.book;

import com.xwder.api.book.config.BookInfoServiceFeignConfig;
import com.xwder.api.book.fallback.BookInfoServiceFallbackFactory;
import com.xwder.framework.domain.book.BookChapter;
import com.xwder.framework.domain.book.BookInfo;
import com.xwder.framework.utils.message.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: xwder
 * @Date: 2019/7/16 01:13
 * @Description:
 */
@FeignClient(name  = "XWDER-PROVIDER-BOOK", fallbackFactory = BookInfoServiceFallbackFactory.class, configuration = BookInfoServiceFeignConfig.class)
public interface BookInfoServiceApi {


    /**
     * 分页查询书籍信息
     *
     * @param page
     * @param size
     * @param sortField
     * @param order
     * @return
     */
    @RequestMapping("/book/book/page/{page}/{size}")
    public Result<BookInfo> listBookByPage(@PathVariable Integer page, @PathVariable Integer size,
                                           @RequestParam("sortField") final String sortField,
                                           @RequestParam("order") final Sort.Direction order);

    /**
     * 根据书籍信息 查询章节信息
     *
     * @param bookId
     * @param page
     * @param size
     * @param sortField
     * @param order
     * @return
     */
    @RequestMapping("/book/chapter/page/{bookId}/{page}/{size}")
    public Result<BookChapter> listBookChapterByPage(@PathVariable Integer bookId, @PathVariable Integer page, @PathVariable Integer size,
                                                     @RequestParam("sortField") final String sortField,
                                                     @RequestParam("order") final Sort.Direction order);

}

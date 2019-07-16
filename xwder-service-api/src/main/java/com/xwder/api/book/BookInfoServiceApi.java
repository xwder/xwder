package com.xwder.api.book;

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

@FeignClient(value = "XWDER-PROVIDER-BOOK")
public interface BookInfoServiceApi {


    @RequestMapping("/book/book/page/{page}/{size}")
    public Result<BookInfo> listBookByPage(@PathVariable Integer page, @PathVariable Integer size,
                                           @RequestParam("sortField") final String sortField,
                                           @RequestParam("order") final Sort.Direction order);

    @RequestMapping("/book/chapter/page/{page}/{size}")
    public Result<BookChapter> listBookChapterByPage(@PathVariable Integer page, @PathVariable Integer size,
                                                     @RequestParam("sortField") final String sortField,
                                                     @RequestParam("order") final Sort.Direction order);
}

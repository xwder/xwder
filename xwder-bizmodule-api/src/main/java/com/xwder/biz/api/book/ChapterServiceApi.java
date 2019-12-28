package com.xwder.biz.api.book;

import com.xwder.biz.api.book.fallback.BookInfoServiceFallbackFactory;
import com.xwder.cloud.commmon.api.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 图书模块api
 *
 * @author wande
 * @version 1.0
 * @date 2019/12/26
 */
@FeignClient(name = "xwder-biz-book", fallbackFactory = BookInfoServiceFallbackFactory.class)
@RequestMapping(value = "/book")
public interface ChapterServiceApi {

    /**
     * 分页查询书籍章节信息信息
     *
     * @param bookId      bookId
     * @param withContent 是否返回章节内容 0不返回，非0返回
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/chapters", method = RequestMethod.GET)
    CommonResult listChapterByBookId(@RequestParam(value = "bookId", required = true) Integer bookId,
                                     @RequestParam(value = "withContent", defaultValue = "1") Integer withContent,
                                     @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                     @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize);

}

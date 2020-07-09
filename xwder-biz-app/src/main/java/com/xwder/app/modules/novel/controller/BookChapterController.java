package com.xwder.app.modules.novel.controller;

import com.xwder.app.modules.novel.entity.BookChapter;
import com.xwder.app.modules.novel.service.intf.BookChapterService;
import com.xwder.app.modules.novel.service.intf.BookInfoService;
import com.xwder.cloud.commmon.api.CommonResult;
import com.xwder.cloud.commmon.constan.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * @author wande
 * @version 1.0
 * @date 2020/07/07
 */
@Validated
@RequestMapping(value = "/novel/chapters")
@RestController
public class BookChapterController {

    @Autowired
    private BookInfoService bookInfoService;

    @Autowired
    private BookChapterService bookChapterService;

    @RequestMapping(value = "/{bookId}")
    public CommonResult listBookChapterByBookId(@PathVariable @Min(1) @Max(10000) Integer bookId,
                                                @RequestParam(value = "pageNum", required = false) Integer pageNum,
                                                @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        pageNum = pageNum == null ? Constant.DEFAULT_PAGE_NUM : pageNum;
        pageSize = pageSize == null ? Constant.DEFAULT_PAGE_SIZE : pageSize;
        Page<BookChapter> bookChapterPage = bookChapterService.listBookChapterByBookId(bookId, pageNum, pageSize);
        return CommonResult.success(bookChapterPage);
    }

    @RequestMapping(value = "")
    public CommonResult listBookChapterByBookName(@RequestParam(value = "bookName") String bookName,
                                                @RequestParam(value = "pageNum", required = false) Integer pageNum,
                                                @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        pageNum = pageNum == null ? Constant.DEFAULT_PAGE_NUM : pageNum;
        pageSize = pageSize == null ? Constant.DEFAULT_PAGE_SIZE : pageSize;
        Page<BookChapter> bookChapterPage = bookChapterService.listBookChapterByBookName(bookName, pageNum, pageSize);
        return CommonResult.success(bookChapterPage);
    }

}

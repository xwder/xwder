package com.xwder.biz.book.controller;

import com.xwder.biz.api.book.ChapterServiceApi;
import com.xwder.biz.book.service.intf.ChapterService;
import com.xwder.biz.model.book.BookChapter;
import com.xwder.cloud.commmon.api.CommonPage;
import com.xwder.cloud.commmon.api.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: xwder
 * @Date: 2019/7/15 23:39
 * @Description:
 */
@RequestMapping(value = "/book")
@RestController
public class ChapterController implements ChapterServiceApi {

    @Autowired
    private ChapterService chapterService;

    @RequestMapping(value = "/chapters", method = RequestMethod.GET)
    @Override
    public CommonResult listChapterByBookId(@RequestParam(value = "bookId", required = true) Integer bookId,
                                            @RequestParam(value = "withContent", defaultValue = "0") Integer withContent,
                                            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        List<BookChapter> bookChapterList = chapterService.listChapterByBookId(bookId, withContent, pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(bookChapterList));
    }

    @Override
    @GetMapping(value = "/latestChapters")
    public CommonResult getLatestChapter(
            @RequestParam(value = "author") String author,
            @RequestParam(value = "bookName") String bookName,
            @RequestParam(value = "bookUrl", required = true) String bookUrl){
        return chapterService.getLatestChapters(author,bookName,bookUrl);
    }
}
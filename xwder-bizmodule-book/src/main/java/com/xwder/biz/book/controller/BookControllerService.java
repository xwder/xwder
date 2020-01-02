package com.xwder.biz.book.controller;

import com.github.pagehelper.PageInfo;
import com.xwder.biz.api.book.BookInfoServiceApi;
import com.xwder.biz.book.service.intf.BookService;
import com.xwder.biz.model.book.BookInfo;
import com.xwder.cloud.commmon.api.CommonPage;
import com.xwder.cloud.commmon.api.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: xwder
 * @Date: 2019/7/15 23:39
 * @Description:
 */
@RequestMapping(value = "/book")
@RestController
public class BookControllerService implements BookInfoServiceApi {

    @Autowired
    private BookService bookService;

    @RequestMapping(value = "/bookinfos", method = RequestMethod.GET)
    @Override
    public CommonResult listBook(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                 @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        List<BookInfo> bookInfoList = bookService.listBookInfo(pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(bookInfoList));
    }
}
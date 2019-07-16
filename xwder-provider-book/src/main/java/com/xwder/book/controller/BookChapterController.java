package com.xwder.book.controller;

import com.xwder.book.service.BookChapterService;
import com.xwder.framework.domain.book.BookChapter;
import com.xwder.framework.utils.message.Result;
import com.xwder.framework.utils.message.ResultUtil;
import com.xwder.framework.utils.page.PageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: xwder
 * @Date: 2019/7/15 23:55
 * @Description:
 */
@Controller
@RequestMapping("/book/chapter")
public class BookChapterController {

    @Autowired
    private BookChapterService bookChapterService;

    @GetMapping("/page/{page}/{size}")
    @ResponseBody
    public Result<BookChapter> listByPage(@PathVariable Integer page, @PathVariable Integer size, String sortField, Sort.Direction order) {
        PageData pageInfo = bookChapterService.findAll(page, size, sortField, order);
        return ResultUtil.success(pageInfo);
    }


}

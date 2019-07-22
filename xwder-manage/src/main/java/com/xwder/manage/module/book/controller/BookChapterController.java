package com.xwder.manage.module.book.controller;

import com.xwder.framework.domain.book.BookChapter;
import com.xwder.framework.domain.book.BookInfo;
import com.xwder.framework.utils.message.Result;
import com.xwder.framework.utils.page.PageData;
import com.xwder.manage.framework.web.controller.BaseController;
import com.xwder.manage.framework.web.page.TableDataInfo;
import com.xwder.manage.framework.web.page.TableSupport;
import com.xwder.manage.module.book.service.BookChapterService;
import com.xwder.manage.module.book.service.BookInfoService;
import com.xwder.manage.module.message.service.BookUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: xwder
 * @Date: 2019/7/15 00:13
 * @Description:
 */
@RequestMapping("/book/chapter")
@Controller
public class BookChapterController extends BaseController {


    @Autowired
    private BookChapterService bookChapterService;


    @GetMapping("/page")
    public String tag(Model model) {
        return "book/chapter";
    }

    @GetMapping("/list/{bookId}")
    @ResponseBody
    public TableDataInfo list(@PathVariable Integer bookId) {
        pageDomain = TableSupport.buildPageRequest();
        Integer pageNum = pageDomain.getPageNum() - 1;
        Integer pageSize = pageDomain.getPageSize();
        PageData page = bookChapterService.findAll(bookId, pageNum, pageSize, null, null);
        TableDataInfo rspData = new TableDataInfo();
        if (page == null) {
            rspData.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            rspData.setRows(null);
            rspData.setTotal(0);
            return rspData;
        }
        rspData.setCode(0);
        rspData.setRows(page.getData());
        rspData.setTotal(page.getTotal());
        return rspData;
    }

}

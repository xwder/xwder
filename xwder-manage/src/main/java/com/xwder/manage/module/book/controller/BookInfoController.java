package com.xwder.manage.module.book.controller;

import com.xwder.framework.domain.book.BookInfo;
import com.xwder.framework.utils.page.PageData;
import com.xwder.manage.framework.web.controller.BaseController;
import com.xwder.manage.framework.web.page.TableDataInfo;
import com.xwder.manage.framework.web.page.TableSupport;
import com.xwder.manage.module.book.service.BookInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: xwder
 * @Date: 2019/7/15 00:13
 * @Description:
 */
@RequestMapping("/book/book")
@Controller
public class BookInfoController extends BaseController {


    @Autowired
    private BookInfoService bookInfoService;


    @GetMapping("/page")
    public String tag(Model model) {
        return "book/book";
    }

    @GetMapping("/list")
    @ResponseBody
    public TableDataInfo list(BookInfo bookInfo) {
        pageDomain = TableSupport.buildPageRequest();
        Integer pageNum = pageDomain.getPageNum() - 1;
        Integer pageSize = pageDomain.getPageSize();
        PageData page = bookInfoService.listBookInfo(pageNum, pageSize, null, null);
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

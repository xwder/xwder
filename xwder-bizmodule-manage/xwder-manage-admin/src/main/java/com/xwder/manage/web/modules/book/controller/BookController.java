package com.xwder.manage.web.modules.book.controller;

import com.xwder.manage.common.core.controller.BaseController;
import com.xwder.manage.common.core.page.PageDomain;
import com.xwder.manage.common.core.page.TableDataInfo;
import com.xwder.manage.common.core.page.TableSupport;
import com.xwder.manage.web.modules.book.dto.BookInfoDto;
import com.xwder.manage.web.modules.book.service.intf.IBookService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * 书籍模块-书籍
 *
 * @author wande
 * @version 1.0
 * @date 2019/12/29
 */
@Controller
@RequestMapping("/book/book")
public class BookController extends BaseController {

    @Autowired
    private IBookService iBookService;

    private String prefix = "modules/book";

    @RequiresPermissions("book:book:view")
    @GetMapping(value = "/bookinfos")
    public String user() {
        return prefix + "/book/book";
    }

    @CrossOrigin
    @RequiresPermissions("book:book:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(BookInfoDto bookInfoDto) {
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        return iBookService.listBookInfo(pageNum, pageSize, bookInfoDto);
    }
}

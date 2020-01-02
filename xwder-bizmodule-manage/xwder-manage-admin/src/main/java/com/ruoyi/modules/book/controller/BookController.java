package com.ruoyi.modules.book.controller;

import com.google.common.collect.Lists;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.PageDomain;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.page.TableSupport;
import com.ruoyi.modules.book.dto.BookInfoDto;
import com.ruoyi.modules.book.service.intf.IBookService;
import com.ruoyi.system.domain.SysUser;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

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

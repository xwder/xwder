package com.ruoyi.modules.book.controller;

import com.google.common.collect.Lists;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.system.domain.SysUser;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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

    private String prefix = "modules/book";

    @RequiresPermissions("book:book:view")
    @GetMapping(value = "/bookinfos")
    public String user() {
        return prefix + "/book/book";
    }

    @RequiresPermissions("book:book:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysUser user) {
        return getDataTable(Lists.newArrayList());
    }
}

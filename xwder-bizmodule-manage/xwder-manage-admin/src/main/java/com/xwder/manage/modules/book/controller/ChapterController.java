package com.xwder.manage.modules.book.controller;

import com.xwder.manage.common.core.controller.BaseController;
import com.xwder.manage.common.core.page.PageDomain;
import com.xwder.manage.common.core.page.TableDataInfo;
import com.xwder.manage.common.core.page.TableSupport;
import com.xwder.manage.modules.book.dto.BookChapterDto;
import com.xwder.manage.modules.book.dto.BookInfoDto;
import com.xwder.manage.modules.book.service.intf.IBookService;
import com.xwder.manage.modules.book.service.intf.IChapterService;
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
@RequestMapping("/book/chapter")
public class ChapterController extends BaseController {

    @Autowired
    private IBookService iBookService;

    @Autowired
    private IChapterService iChapterService;

    private String prefix = "modules/book";

    @RequiresPermissions("book:chapter:view")
    @GetMapping(value = "/chapters")
    public String user() {
        return prefix + "/chapter/chapter";
    }

    @CrossOrigin
    @RequiresPermissions("book:chapter:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(BookChapterDto bookChapterDto) throws Exception {
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        return iChapterService.listChapters(pageNum, pageSize, bookChapterDto);
    }
}

package com.xwder.app.sysmodules.novel.contorller;

import com.xwder.app.common.result.CommonResult;
import com.xwder.app.modules.novel.entity.BookChapter;
import com.xwder.app.modules.novel.entity.BookInfo;
import com.xwder.app.modules.novel.service.intf.BookChapterService;
import com.xwder.app.modules.novel.service.intf.BookInfoService;
import com.xwder.app.sysmodules.novel.dto.BookInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author xwder
 * @Description: novel controller
 * @date 2020/9/3 22:57
 */
@RequestMapping(value = "/sys/novel")
@Controller
public class NovelController {

    @Autowired
    private BookInfoService bookInfoService;

    @Autowired
    private BookChapterService bookChapterService;

    /**
     * 书籍列表页面
     *
     * @return
     */
    @RequestMapping(value = "/bookListPage")
    public String listBooInfoPage() {
        return "sys/novel/novelList";
    }

    /**
     * 章节列表页面
     *
     * @return
     */
    @RequestMapping(value = "/chapterListPage")
    public String listChapterPage(@RequestParam(defaultValue = "1", value = "bookId", required = false) Integer bookId, Model model) {
        model.addAttribute("bookId", bookId);
        return "sys/novel/chapterList";
    }

    /**
     * 书籍列表
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/bookList")
    public Object listBookInfo(@Validated BookInfoDto bookInfoDto) {
        Page<BookInfo> booksPage = bookInfoService.listBookInfo(bookInfoDto);
        CommonResult<Page<BookInfo>> commonResult = CommonResult.success(booksPage);
        return commonResult;
    }

    /**
     * 章节列表
     *
     * @param bookId
     * @param page
     * @param limit
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/chapterList")
    public Object listChapter(@RequestParam(defaultValue = "1", value = "bookId", required = false) Integer bookId,
                              Integer page, Integer limit) {
        Page<BookChapter> bookChapterPage = bookChapterService.listBookChapterByBookId(bookId, page, limit, null);
        CommonResult commonResult = CommonResult.success(bookChapterPage);
        return commonResult;
    }
}

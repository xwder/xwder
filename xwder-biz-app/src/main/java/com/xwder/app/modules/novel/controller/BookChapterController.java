package com.xwder.app.modules.novel.controller;

import com.xwder.app.modules.novel.entity.BookChapter;
import com.xwder.app.modules.novel.entity.BookInfo;
import com.xwder.app.modules.novel.service.intf.BookChapterService;
import com.xwder.app.modules.novel.service.intf.BookInfoService;
import com.xwder.cloud.commmon.api.CommonResult;
import com.xwder.cloud.commmon.constan.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * @author wande
 * @version 1.0
 * @date 2020/07/07
 */
@Validated
@RequestMapping(value = "/book")
@Controller
public class BookChapterController {

    @Autowired
    private BookInfoService bookInfoService;

    @Autowired
    private BookChapterService bookChapterService;

    /**
     * 根据bookId 查询章节信息
     *
     * @param bookId
     * @param pageNum
     * @param pageSize
     * @param order
     * @return json
     */
    @ResponseBody
    @PostMapping(value = "/{bookId}")
    public CommonResult listBookChapterByBookId(@PathVariable @Min(1) @Max(100000) Integer bookId,
                                                @RequestParam(value = "pageNum", required = false) Integer pageNum,
                                                @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                                @RequestParam(value = "order", required = false) String order) {
        pageNum = pageNum == null ? Constant.DEFAULT_PAGE_NUM : pageNum;
        pageSize = pageSize == null ? Constant.DEFAULT_PAGE_SIZE : pageSize;
        Page<BookChapter> bookChapterPage = bookChapterService.listBookChapterByBookId(bookId, pageNum, pageSize, order);
        return CommonResult.success(bookChapterPage);
    }


    /**
     * 根据bookId 查询章节信息
     *
     * @param bookId
     * @param pageNum
     * @param pageSize
     * @param order
     * @return 章节页面
     */
    @GetMapping(value = "/{bookId}")
    public String listBookChapterByBookId(@PathVariable @Min(1) @Max(100000) Integer bookId,
                                          @RequestParam(value = "pageNum", required = false) Integer pageNum,
                                          @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                          @RequestParam(value = "order", required = false) String order, Model model) {
        pageNum = pageNum == null ? Constant.DEFAULT_PAGE_NUM : pageNum;
        pageSize = pageSize == null ? Constant.DEFAULT_PAGE_SIZE : pageSize;
        Page<BookChapter> bookChapterPage = bookChapterService.listBookChapterByBookId(bookId, pageNum, pageSize, order);
        BookInfo bookInfo = bookInfoService.getBookInfoById(bookId);

        model.addAttribute("bookChapterPage", bookChapterPage);
        model.addAttribute("bookInfo", bookInfo);

        return "book/chapters";
    }

    /**
     * 章节详情页面
     *
     * @param bookId
     * @param chapterId
     * @param model
     * @return
     */
    @GetMapping(value = "/{bookId}/{chapterId}")
    public String getChapterInfoByBookIdAndChapterId(@PathVariable @Min(1) @Max(100000) Integer bookId,
                                                     @PathVariable @Min(1) @Max(20000) Integer chapterId,
                                                     Model model) {
        BookChapter bookChapter = bookChapterService.getBookChapterByBookIdAndChapterId(bookId, chapterId);
        model.addAttribute("bookChapter", bookChapter);
        return "book/chapter";
    }
}

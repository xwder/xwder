package com.xwder.app.modules.novel.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.xwder.app.consts.SysConstant;
import com.xwder.app.modules.novel.entity.BookChapter;
import com.xwder.app.modules.novel.entity.BookInfo;
import com.xwder.app.modules.novel.service.intf.BookChapterService;
import com.xwder.app.modules.novel.service.intf.BookInfoService;
import com.xwder.app.utils.HtmlUtils;
import com.xwder.cloud.commmon.api.CommonResult;
import com.xwder.cloud.commmon.constan.Constant;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.Year;
import java.util.List;

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
    @RequestMapping(value = "/{bookId}")
    public String listBookChapterByBookId(@PathVariable @Min(1) @Max(100000) Integer bookId,
                                          @RequestParam(value = "pageNum", required = false) Integer pageNum,
                                          @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                          @RequestParam(value = "order", required = false) String order, Model model) {


        BookInfo bookInfo = bookInfoService.getBookInfoById(bookId);
        model.addAttribute("bookInfo", bookInfo);

        if (bookInfo == null) {
            return "book/chapter";
        }
        String author = bookInfo.getAuthor();
        List<BookInfo> bookInfoList = bookInfoService.listBookInfoByAuthor(author);
        // 作品总数
        model.addAttribute("bookNum", bookInfoList.size());
        // 总字数
        Integer totalWords = 0;
        for (BookInfo book : bookInfoList) {
            totalWords += book.getWordSize();
        }
        model.addAttribute("totalWords", totalWords/10000);

        Page<BookChapter> bookChapterPage = bookChapterService.listBookChapterByBookId(bookId, 1, 20000, SysConstant.order_asc);
        List<BookChapter> bookChapterList = bookChapterPage.getContent();
        model.addAttribute("bookChapterList", bookChapterList);
        if (CollectionUtil.isNotEmpty(bookChapterList)) {
            BookChapter lastChapter = bookChapterList.get(bookChapterList.size() - 1);
            model.addAttribute("lastChapter", lastChapter);
            String lastChapterContentStr = HtmlUtils.htmmConvertTxt(lastChapter.getChapterContent());
            model.addAttribute("lastChapterContent", StringUtils.left(lastChapterContentStr,50));
            model.addAttribute("startChapter", bookChapterList.get(0));
        }

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
    @RequestMapping(value = "/{bookId}/{chapterId}")
    public String getChapterInfoByBookIdAndChapterId(@PathVariable @Min(1) @Max(100000) Integer bookId,
                                                     @PathVariable @Min(1) @Max(20000) Integer chapterId,
                                                     Model model) {

        return "book/chapter";
    }
}

package com.xwder.biz.book.service.intf;

import com.xwder.biz.model.book.BookChapter;
import com.xwder.cloud.commmon.api.CommonResult;

import java.util.List;

/**
 * 图书模块章节Service
 *
 * @author wande
 * @version 1.0
 * @date 2019/12/27
 */
public interface ChapterService {

    /**
     * 根据书籍信息查询章节信息
     *
     * @param bookId      bookId
     * @param withContent 是否返回章节内容 0不返回，非0返回
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<BookChapter> listChapterByBookId(Integer bookId, Integer withContent, Integer pageNum, Integer pageSize);


    /**
     * 获取最新章节
     *
     * @param author
     * @param bookName
     * @param bookUrl
     * @return
     */
    CommonResult getLatestChapters(String author, String bookName, String bookUrl);

    /**
     * 获取没有爬取内容的章节信息
     *
     * @param bookChapter
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<BookChapter> listNoContentChapter(BookChapter bookChapter, Integer pageNum, Integer pageSize);

    void spiderChapterInfo(BookChapter bookChapter);

    void spiderChapterInfoConcurrent();
}

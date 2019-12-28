package com.xwder.biz.book.service.intf;

import com.xwder.biz.model.book.BookChapter;

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


}

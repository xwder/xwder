package com.xwder.manage.modules.book.service.intf;

import com.xwder.manage.modules.book.dto.BookChapterDto;

import java.util.List;

/**
 * @author wande
 * @version 1.0
 * @date 2020/01/08
 */
public interface IChapterService {


    /**
     * 获取最新章节
     *
     * @param author
     * @param bookName
     * @param bookUrl
     * @return
     */
    List<BookChapterDto> getLatestChapters(String author, String bookName, String bookUrl);

    /**
     * 文章更新推送
     *
     * @param author
     * @param bookName
     * @param bookUrl
     */
    void bookUpdateNotice(String author, String bookName, String bookUrl);
}

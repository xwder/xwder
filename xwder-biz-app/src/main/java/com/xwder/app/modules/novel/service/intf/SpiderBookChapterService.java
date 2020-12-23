package com.xwder.app.modules.novel.service.intf;

import com.xwder.app.modules.novel.entity.BookChapter;

import java.util.List;

/**
 * @author xwder
 * @date 2020年12月17日22:59:19
 */
public interface SpiderBookChapterService {

    /**
     * 根据书籍url爬取数据章节列表
     *
     * @param bookUrl
     * @return
     */
    List<BookChapter> spiderBookChapterByBookUrl(String bookUrl);
}

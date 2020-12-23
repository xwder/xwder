package com.xwder.app.modules.novel.service.intf;

import com.xwder.app.modules.novel.entity.BookInfo;

import java.util.List;

/**
 * 书籍内容爬取
 *
 * @author: xwder
 * @date: 2020/12/19 15 24
 */
public interface SpiderBookService {

    /**
     * 根据书籍分页爬取书籍信息
     * @param bookPageUrl 书籍分页url
     * @return 书籍分页的书籍列表
     */
    List<BookInfo> spiderBookInfoByBookPageUrl(String bookPageUrl);
}

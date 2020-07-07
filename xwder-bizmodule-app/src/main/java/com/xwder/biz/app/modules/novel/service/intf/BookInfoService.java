package com.xwder.biz.app.modules.novel.service.intf;

import com.xwder.biz.app.modules.novel.entity.BookInfo;
import com.xwder.cloud.commmon.api.CommonResult;

import java.util.List;

/**
 * 书籍 service
 *
 * @author xwder
 */
public interface BookInfoService {

    /**
     * 根据书籍名称下载txt
     *
     * @param bookName
     * @return
     */
    CommonResult downBookByBookName(String bookName);

    /**
     * 根据书名获取书籍
     *
     * @param bookName
     * @return
     */
    List<BookInfo> listBookInfoByBookName(String bookName);

    /**
     * 根据id获取书籍信息
     *
     * @param id
     * @return
     */
    BookInfo getBookInfoById(Integer id);

}

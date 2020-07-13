package com.xwder.app.modules.novel.service.intf;

import com.xwder.app.modules.novel.entity.BookChapter;
import com.xwder.app.modules.novel.entity.BookInfo;
import com.xwder.cloud.commmon.api.CommonResult;
import org.springframework.data.domain.Page;

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


    /**
     * 分页查询书籍信息
     *
     * @param category
     * @param pageNum
     * @param pageSize
     * @return
     */
    Page<BookInfo> listBookInfo(String category, Integer pageNum, Integer pageSize);

}

package com.xwder.app.modules.novel.service.intf;

import com.xwder.app.common.result.CommonResult;
import com.xwder.app.modules.novel.entity.BookInfo;
import com.xwder.app.sysmodules.novel.dto.BookInfoDto;
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
     * 根据bookId下载txt
     *
     * @param bookId
     * @return
     */
    CommonResult downBookByBookId(Integer bookId);

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
     * 根据bookUrl获取书籍
     *
     * @param bookUrl 书籍url
     * @return 书籍信息
     */
    BookInfo getBookInfoByBookUrl(String bookUrl);


    /**
     * 分页查询书籍信息
     *
     * @param category
     * @param pageNum
     * @param pageSize
     * @return
     */
    Page<BookInfo> listBookInfo(String category, Integer pageNum, Integer pageSize);

    /**
     * 分页查询书籍信息
     *
     * @return
     */
    Page<BookInfo> listBookInfo(BookInfoDto bookInfoDto);

    /**
     * 分页查询书籍信息
     *
     * @param bookName
     * @param pageNum
     * @param pageSize
     * @return
     */
    Page<BookInfo> listBookInfoByBookName(String bookName, Integer pageNum, Integer pageSize);


    /**
     * 根据author查询所有书籍
     *
     * @param author
     * @return
     */
    List<BookInfo> listBookInfoByAuthor(String author);

    /**
     * 从本地获取章节内容
     *
     * @param bookId
     * @param chapterId
     * @return
     */
    String getLocalChapterContent(Integer bookId, Integer chapterId);

}

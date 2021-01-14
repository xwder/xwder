package com.xwder.app.modules.novel.service.intf;

import com.xwder.app.modules.novel.entity.BookChapter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 章节 service
 *
 * @author xwder
 */
public interface BookChapterService {

    /**
     * 根据bookId查询所有章节信息
     * @param bookId 书籍id
     * @return 所有章节信息
     */
    List<BookChapter> findAllByBookId(Integer bookId);

    /**
     * 根据bookId查询章节信息
     * @param bookId 书籍id
     * @param pageable 分页信息
     * @return 章节信息
     */
    List<BookChapter> findAllByBookIdPageable(Integer bookId, Pageable pageable);

    /**
     * 根据bookId分页查询章节信息
     *
     * @param bookId
     * @param pageNum
     * @param pageSize
     * @param order    id 排序 desc asc
     * @return
     */
    Page<BookChapter> listBookChapterByBookId(Integer bookId, Integer pageNum, Integer pageSize, String order);

    /**
     * 根据bookName分页查询章节信息
     *
     * @param bookName
     * @param pageNum
     * @param pageSize
     * @return
     */
    Page<BookChapter> listBookChapterByBookName(String bookName, Integer pageNum, Integer pageSize);


    /**
     * 根据书籍编号和章节编号查询章节信息
     *
     * @param bookId
     * @param chapterId
     * @return
     */
    BookChapter getBookChapterByBookIdAndChapterId(Integer bookId, Integer chapterId);

    /**
     * 爬取章节内容详情
     *
     * @param bookChapter 章节信息
     * @return
     */
    BookChapter spiderChapterContent(BookChapter bookChapter);

    /**
     * 根据书籍编号和章节编号 缓存后续章节 加快章节读取速度 缓存后续5章
     *
     * @param bookId 书籍id
     * @param chapterId 章节id
     */
    void cacheBookChapter(Integer bookId, Integer chapterId);

    /** 根据书籍id更新数据最新章节信息
     * @param bookId 书籍id
     * @Description: 根据书籍id更新数据最新章节信息
     * @return 更新状态成功失败
     */
    boolean updateBookChapterByBookId(Integer bookId);
}

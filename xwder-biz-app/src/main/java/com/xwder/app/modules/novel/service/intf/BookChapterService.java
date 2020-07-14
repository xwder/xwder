package com.xwder.app.modules.novel.service.intf;

import com.xwder.app.modules.novel.entity.BookChapter;
import org.springframework.data.domain.Page;

/**
 * 章节 service
 *
 * @author xwder
 */
public interface BookChapterService {

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

}

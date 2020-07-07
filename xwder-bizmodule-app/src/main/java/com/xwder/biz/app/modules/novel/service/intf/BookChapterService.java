package com.xwder.biz.app.modules.novel.service.intf;

import com.xwder.biz.app.modules.novel.entity.BookChapter;
import com.xwder.cloud.commmon.api.CommonResult;
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
     * @return
     */
    Page<BookChapter> listBookChapterByBookId(Integer bookId, Integer pageNum, Integer pageSize);

    /**
     * 根据bookName分页查询章节信息
     *
     * @param bookName
     * @param pageNum
     * @param pageSize
     * @return
     */
    Page<BookChapter> listBookChapterByBookName(String bookName, Integer pageNum, Integer pageSize);

}

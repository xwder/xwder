package com.xwder.biz.app.modules.novel.repository;


import com.xwder.biz.app.modules.novel.entity.BookChapter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookChapterRepository extends JpaRepository<BookChapter, Integer> {

    /**
     * 根据bookId查询
     *
     * @param bookId
     * @return
     */
    List<BookChapter> findAllByBookId(Integer bookId);

    /**
     * 根据bookId查询 Pageable
     *
     * @param bookId
     * @return
     */
    List<BookChapter> findAllByBookId(Integer bookId, Pageable page);

    /**
     * 根据bookId查询章节信息
     *
     * @param bookId
     * @param pageable
     * @return
     */
    Page<BookChapter> findByBookId(Integer bookId, Pageable pageable);

    /**
     * 根据bookName查询章节信息
     *
     * @param bookName
     * @param pageable
     * @return
     */
    Page<BookChapter> findByBookName(String bookName, Pageable pageable);

}

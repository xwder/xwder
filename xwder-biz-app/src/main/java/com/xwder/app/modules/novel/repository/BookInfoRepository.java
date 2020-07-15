package com.xwder.app.modules.novel.repository;


import com.xwder.app.modules.novel.entity.BookChapter;
import com.xwder.app.modules.novel.entity.BookInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookInfoRepository extends JpaRepository<BookInfo, Integer> {

    /**
     * 根据书名查询书籍信息
     * @param bookName
     * @return
     */
    List<BookInfo> findAllByBookName(String bookName);


    /**
     * 根据分类查询书籍信息
     *
     * @param category
     * @param pageable
     * @return
     */
    Page<BookInfo> findByCategory(String category, Pageable pageable);

    /**
     * 根据author查询所有书籍
     *
     * @param author
     * @return
     */
    List<BookInfo> findAllByAuthor(String author);

}

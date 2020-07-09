package com.xwder.app.modules.novel.repository;


import com.xwder.app.modules.novel.entity.BookInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookInfoRepository extends JpaRepository<BookInfo, Integer> {

    List<BookInfo> findAllByBookName(String bookName);

}

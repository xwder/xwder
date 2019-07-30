package com.xwder.book;


import com.xwder.book.dao.BookChapterDao;
import com.xwder.framework.domain.book.BookChapter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BookApplication.class)
public class BookApplicationTests {

    @Autowired
    private BookChapterDao bookChapterDao;

    @Test
    public void listBookChapterByBookIdTest(){
        BookChapter bookChapter = BookChapter.builder().bookId(1).build();
//        List<BookChapter> bookChapterList = bookChapterDao.listBookChapterByBookId(bookChapter);
//        System.out.println(bookChapterList.toString());
    }

}
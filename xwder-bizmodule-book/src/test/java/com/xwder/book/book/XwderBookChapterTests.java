package com.xwder.book.book;

import com.github.pagehelper.PageHelper;
import com.google.common.collect.Maps;
import com.xwder.biz.book.XwderBookApplication;
import com.xwder.biz.book.dao.BookChapterCustomerMapper;
import com.xwder.biz.model.book.BookChapter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = XwderBookApplication.class)
public class XwderBookChapterTests {

    @Autowired
    private BookChapterCustomerMapper bookChapterCustomerMapper;

    @Test
    public void contextLoads() {
    }

    @Test
    public void chaptersTest() {
        BookChapter bookChapter = BookChapter.builder().bookId(1).build();
        PageHelper.startPage(1, 10);
        List<BookChapter> chapterList = bookChapterCustomerMapper.listBookChapterByBookId(bookChapter);
        System.out.println(chapterList);
    }


    @Test
    public void chaptersMapperTest() {
        Map map = Maps.newHashMap();
        map.put("bookId", 1);
        map.put("order", 0);
        map.put("withContent",0);
        PageHelper.startPage(1, 10);
        List<BookChapter> bookChapters = bookChapterCustomerMapper.listBookChapter(map);
        System.out.println(bookChapters);
    }

}

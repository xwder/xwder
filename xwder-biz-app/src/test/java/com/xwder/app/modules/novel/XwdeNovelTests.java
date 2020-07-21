package com.xwder.app.modules.novel;

import com.xwder.app.XwderApplication;
import com.xwder.app.modules.novel.entity.BookChapter;
import com.xwder.app.modules.novel.entity.BookInfo;
import com.xwder.app.modules.novel.repository.BookChapterRepository;
import com.xwder.app.modules.novel.repository.BookInfoRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = XwderApplication.class)
public class XwdeNovelTests {

    @Autowired
    private BookInfoRepository bookInfoRepository;

    @Autowired
    private BookChapterRepository bookChapterRepository;

    /**
     * 测试分表是否成功
     */
    @Test
    public void bookChapterRepositoryTest(){
        List<BookChapter> bookChapterList = bookChapterRepository.findAllByBookId(1001);
        System.out.println(bookChapterList);
    }

    @Test
    public void bookInfoRepositoryTest(){
        List<BookInfo> bookInfoList = bookInfoRepository.findAllByBookName("剑来");
        System.out.println(bookInfoList);
    }

    @Test
    public void findBookChapterByBookIdAndIdTest(){
        BookChapter bookChapter = bookChapterRepository.findBookChapterByBookIdAndId(1, 1);
        System.out.println(bookChapter);
    }
}

package com.xwder.book.book;

import com.github.pagehelper.PageHelper;
import com.google.common.collect.Maps;
import com.xwder.biz.book.XwderBookApplication;
import com.xwder.biz.book.dao.BookChapterCustomerMapper;
import com.xwder.biz.book.dao.BookInfoCustomerMapper;
import com.xwder.biz.book.service.intf.ChapterService;
import com.xwder.biz.model.book.BookChapter;
import com.xwder.biz.model.book.BookInfo;
import com.xwder.biz.model.book.BookInfoExample;
import com.xwder.biz.model.book.mapper.BookInfoMapper;
import org.apache.commons.lang.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Whitelist;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = XwderBookApplication.class)
public class XwderBookChapterTests {

    @Autowired
    private BookChapterCustomerMapper bookChapterCustomerMapper;

    @Autowired
    private BookInfoCustomerMapper bookInfoCustomerMapper;

    @Autowired
    private BookInfoMapper bookInfoMapper;

    @Autowired
    private ChapterService chapterService;

    @Test
    public void contextLoads() {
    }

    @Test
    public void bookInfoMapperTest(){
        BookInfo bookInfo = bookInfoMapper.selectByPrimaryKey(1);
        System.out.println(bookInfo);
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
        map.put("withContent", 0);
        PageHelper.startPage(1, 10);
        List<BookChapter> bookChapters = bookChapterCustomerMapper.listBookChapter(map);
        System.out.println(bookChapters);

        BookInfoExample bookInfoExample = new BookInfoExample();
        List<BookInfo> bookInfos = bookInfoCustomerMapper.selectAll();
        System.out.println(bookInfos);
    }

    @Test
    public void listNoContentChapterTest() {
        BookChapter bookChapter = BookChapter.builder().build();
        List<BookChapter> chapterList = chapterService.listNoContentChapter(bookChapter, 1, 10);
        System.out.println(chapterList);
    }

    @Test
    public void jsoupTest() throws Exception {
        BookChapter queryBookChapter = BookChapter.builder().build();
        List<BookChapter> chapterList = chapterService.listNoContentChapter(queryBookChapter, 1, 10);
        if (chapterList.size() > 0) {
            BookChapter bookChapter = chapterList.get(0);
            Map<String, String> header = new HashMap<String, String>();
            header.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
            header.put("Accept-Language", "zh-cn,zh;q=0.5");
            header.put("Accept-Charset", "  GB2312,utf-8;q=0.7,*;q=0.7");
            header.put("Connection", "keep-alive");
            //header.put("cookie", "acw_tc=2760820d15766480375335721edd64371fcb400d47561f8896c6e28c7d3b40; uuid_tt_dd=10_9926153920-1576648097988-182265; dc_session_id=10_1576648097988.363322; Hm_lvt_6bcd52f51e9b3dce32bec4a3997715ac=1576644694,1576648099; Hm_ct_6bcd52f51e9b3dce32bec4a3997715ac=6525*1*10_9926153920-1576648097988-182265; announcement=%257B%2522isLogin%2522%253Afalse%252C%2522announcementUrl%2522%253A%2522https%253A%252F%252Fblogdev.blog.csdn.net%252Farticle%252Fdetails%252F103053996%2522%252C%2522announcementCount%2522%253A0%252C%2522announcementExpire%2522%253A3600000%257D; dc_tos=q2p1ir; Hm_lpvt_6bcd52f51e9b3dce32bec4a3997715ac=1576648180; c-login-auto=3; acw_sc__v2=5df9bf415fd91f48063efe6c7f23be98ca36f37c");
            header.put("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.88 Safari/537.36");
            header.put("referer", bookChapter.getSourceUrl());

            Document document = Jsoup.connect(bookChapter.getSourceUrl())
                    .headers(header)
                    .get();
            Element content = document.getElementById("content");
            Document.OutputSettings outputSettings = new Document.OutputSettings().prettyPrint(false);
            Document doc = Jsoup.parse(content.html());
            doc.outputSettings(outputSettings);
            doc.select("br").append("\\n");
            doc.select("p").prepend("\\n");
            doc.select("p").append("\\n");
            String newHtml = doc.html().replaceAll("\\\\n", "\n");
            String plainText = Jsoup.clean(newHtml, "", Whitelist.none(), outputSettings);
            String result = StringEscapeUtils.unescapeHtml(plainText.trim());

            File fp = new File("D:\\a.txt");
            PrintWriter pfp = new PrintWriter(fp);
            pfp.print(result);
            pfp.close();
            System.out.println(document.toString());
        }
    }

    @Test
    public void spiderChapterInfoConcurrentTest(){
        chapterService.spiderChapterInfoConcurrent();
    }
}

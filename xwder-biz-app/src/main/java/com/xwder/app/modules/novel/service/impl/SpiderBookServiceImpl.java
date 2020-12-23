package com.xwder.app.modules.novel.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import com.xwder.app.consts.SpiderConstant;
import com.xwder.app.modules.novel.entity.BookInfo;
import com.xwder.app.modules.novel.service.intf.SpiderBookService;
import com.xwder.app.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

/**
 * @author: xwder
 * @date: 2020/12/19 15 26
 */
@Slf4j
@Service
public class SpiderBookServiceImpl implements SpiderBookService {

    /**
     * key-value:书籍分类-该分页书籍页数
     */
    public static Map<Integer, Integer> categoryMap = new HashMap<>();

    static {
        categoryMap.put(1, 1353);
        categoryMap.put(2, 364);
        categoryMap.put(3, 936);
        categoryMap.put(4, 10);
        categoryMap.put(5, 3);
        categoryMap.put(6, 281);
        categoryMap.put(7, 352);
    }

    /**
     * 书籍分页url 模板 分类_页码
     */
    private final String bookPageUrl = "http://www.shuquge.com/category/%s_%s.html";

    /**
     * 获取所有书籍分页列表url
     */
    private List<String> calculateBookPageList() {
        ArrayList<String> bookPageUrlList = Lists.newArrayListWithCapacity(100000);
        Set<Integer> categorySet = categoryMap.keySet();
        for (Integer category : categorySet) {
            Integer categoryPageSize = categoryMap.get(category);
            for (int index = 1; index < categoryPageSize; index++) {
                bookPageUrlList.add(String.format(bookPageUrl, category + "", categoryPageSize + ""));
            }
        }
        return bookPageUrlList;
    }


    /**
     * 根据书籍分页爬取书籍信息
     *
     * @param bookPageUrl 书籍分页url
     * @return 书籍分页的书籍列表
     */
    @Override
    public List<BookInfo> spiderBookInfoByBookPageUrl(String bookPageUrl) {
        long startTime = System.currentTimeMillis();

        Map<String, String> header = new HashMap<>(16);
        header.putAll(SpiderConstant.SQG_SPIDER_HEADER_MAP);
        //bookUrl = "http://www.shuquge.com/txt/117367/index.html";
        header.put("referer", bookPageUrl);

        Document document;
        try {
            document = Jsoup.connect(bookPageUrl)
                    .headers(header)
                    .get();
        } catch (IOException e) {
            log.error("爬取书籍分页[{}]信息失败,耗时[{}],错误信息[{}]", bookPageUrl,
                    DateUtil.diffTime(startTime, System.currentTimeMillis()), e);
            return Lists.newArrayList();
        }

        List<BookInfo> bookInfoList = new ArrayList<>();
        // 书籍列表
        Elements liElements = document.select(".wrap .up .l bd ul li");
        if (CollectionUtil.isNotEmpty(liElements)) {
            for (Element liElement : liElements) {
                Elements spanElements = liElement.getElementsByTag("span");
                if (CollectionUtil.isNotEmpty(spanElements) && spanElements.size() == 5) {
                    // 书籍分类 [玄幻魔法]
                    Element categoryNameElement = spanElements.get(0);
                    String categoryName = categoryNameElement != null ? categoryNameElement.text() : "";
                    Element bookNameElement = spanElements.get(1);
                    String bookName = "";
                    String bookUrl = "";
                    if (bookNameElement != null) {
                        Elements bookNameATagElements = bookNameElement.getElementsByTag("a");
                        if (bookNameATagElements != null && bookNameATagElements.size() == 1) {
                            Element bookNameATagElement = bookNameATagElements.get(0);
                            bookName = bookNameATagElement.text();
                            bookUrl = bookNameATagElement.attr("href");
                        }
                    }
                    Element lastChapterElement = spanElements.get(2);
                    String lastChapterName = lastChapterElement != null ? lastChapterElement.text() : "";
                    BookInfo bookInfo = new BookInfo();
                    if (StrUtil.isNotEmpty(bookName) && StrUtil.isNotEmpty(bookUrl)) {
                        bookInfo.setBookName(bookName);
                        bookInfo.setBookUrl(bookUrl);
                        bookInfo.setLatestChapter(lastChapterName);
                        bookInfo.setCategory(categoryName.replace("[", "").replace("]", ""));
                        bookInfoList.add(bookInfo);
                    }
                }
            }
        }
        log.error("爬取书籍分页[{}]信息成功，获取书籍数量[{}],耗时[{}]", bookPageUrl, bookInfoList.size(),
                DateUtil.diffTime(startTime, System.currentTimeMillis()));
        return bookInfoList;
    }
}

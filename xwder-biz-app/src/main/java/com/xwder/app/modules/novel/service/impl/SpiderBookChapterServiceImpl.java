package com.xwder.app.modules.novel.service.impl;


import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import com.xwder.app.consts.SpiderConstant;
import com.xwder.app.modules.novel.entity.BookChapter;
import com.xwder.app.modules.novel.service.intf.SpiderBookChapterService;
import com.xwder.app.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 章节列表爬取
 *
 * @author xwder
 * @date 2020-12-17 23:04:11
 */
@Slf4j
@Service
public class SpiderBookChapterServiceImpl implements SpiderBookChapterService {

    /**
     * 根据书籍url爬取数据章节列表
     *
     * @param bookUrl
     * @return
     */
    @Override
    public List<BookChapter> spiderBookChapterByBookUrl(String bookUrl) {
        Long startTime = System.currentTimeMillis();

        Map<String, String> header = new HashMap<String, String>(16);
        header.putAll(SpiderConstant.SQG_SPIDER_HEADER_MAP);
        //bookUrl = "http://www.shuquge.com/txt/63542/index.html";
        //bookUrl = "http://www.shuquge.com/txt/117367/index.html";
        header.put("referer", bookUrl);

        Document document = null;
        try {
            document = Jsoup.connect(bookUrl)
                    .headers(header)
                    .get();
        } catch (IOException e) {
            log.error("爬取书籍章节[{}]信息失败,耗时[{}],错误信息[{}]", bookUrl,
                    DateUtil.diffTime(startTime, System.currentTimeMillis()), e);
            return Lists.newArrayList();
        }
        // 章节列表
        Elements listMainElement = document.getElementsByClass("listmain");
        if (listMainElement == null) {
            log.error("爬取书籍章节[{}]信息 listmain标签章节目录空 耗时[{}],",
                    bookUrl, DateUtil.diffTime(startTime, System.currentTimeMillis()));
            return Lists.newArrayList();
        }
        Document.OutputSettings outputSettings = new Document.OutputSettings().prettyPrint(false);
        Document doc = Jsoup.parse(listMainElement.html());
        doc.outputSettings(outputSettings);

        // 最新章节 和 正文卷
        Elements dts = doc.getElementsByTag("dt");
        Elements dds = doc.getElementsByTag("dd");

        // 章节大于 12 分为 最新章节和正文卷
        List<BookChapter> bookChapterList = new ArrayList<>();
        if (dts != null && dts.size() == 2 && dds != null && dds.size() > 0) {
            int i = 12;
            // 如果章节数量不足12章
            if (dds.size() <= 24) {
                i = dds.size() / 2;
            }
            for (; i < dds.size(); i++) {
                Element element = dds.get(i);
                // 章节 a 标签
                Elements chapterATags = element.getElementsByTag("a");
                if (chapterATags != null && chapterATags.size() == 1) {
                    Element chapterATag = chapterATags.get(0);
                    String chapterName = chapterATag.text();
                    String href = chapterATag.attr("href");
                    if (StrUtil.isNotBlank(chapterName) && StrUtil.isNotBlank(href)) {
                        String completeChapterUrl = bookUrl.replace("index.html", href);
                        BookChapter chapter = new BookChapter();
                        chapter.setSourceUrl(completeChapterUrl);
                        chapter.setChapterName(chapterName);
                        if (chapterName.contains("章")) {
                            String[] chapterNameSplits = chapterName.split("章");
                            if (chapterNameSplits != null && chapterNameSplits.length == 2) {
                                chapter.setChapterSequence(chapterNameSplits[0].trim() + "章");
                                chapter.setChapterArticle(chapterNameSplits[1].trim());
                            } else if (chapterNameSplits != null && chapterNameSplits.length == 1) {
                                chapter.setChapterSequence(chapterName.trim());
                            }
                        }
                        bookChapterList.add(chapter);
                    }
                }
            }
        }
        log.error("爬取书籍章节[{}]信息成功，获取章节数量[{}],耗时[{}]", bookUrl, bookChapterList.size(),
                DateUtil.diffTime(startTime, System.currentTimeMillis()));
        return bookChapterList;
    }


}

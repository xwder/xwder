package com.xwder.app;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.base.Joiner;
import com.xwder.app.consts.SpiderConstants;
import com.xwder.app.modules.novel.entity.BookChapter;
import com.xwder.app.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

/**
 * @author wande
 * @version 1.0
 * @date 2020/07/21
 */
@Slf4j
public class Test {

    @org.junit.Test
    public void txtTest() {
        File file = new File("D:\\bookchapter-autopartition-long.txt");
        int i = 1;
        for (; i < 97; i++) {
            if (i == 1) {
                String s[] = {"1-1000=1"};
                FileUtil.appendLines(Arrays.asList(s), file, "Utf-8");
            } else {
                String s = ((i - 1) * 1000 + 1) + "-" + (i * 1000) + "=" + i;
                FileUtil.appendLines(Arrays.asList(s), file, "Utf-8");
            }
            i = i++;
        }
    }

    /**
     * 测试guava joiner
     */
    @org.junit.Test
    public void joinerTest() {
        List list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        String join = Joiner.on("-").join(list);
        System.out.println("join: " + join);
    }

    @org.junit.Test
    public void urlEncodeTest() {
        try {
            System.out.println(URLEncoder.encode("http://9di2ey.natappfree.cc/login/qq/getauthcode", "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试mac磁盘文件路径
     */
    @org.junit.Test
    public void macDirTest() {
        File file = new File("/Volumes/本地磁盘 2/novel/1000/1");
        if (file.exists()) {
            File[] files = file.listFiles();
            Arrays.asList(files).stream().forEach(tempFile -> System.out.println(tempFile.getName()));
        }
    }

    @org.junit.Test
    public void spiderBookChapterTest() {
        Long startTime = System.currentTimeMillis();

        Map<String, String> header = new HashMap<String, String>();
        header.putAll(SpiderConstants.SQG_SPIDER_HEADER_MAP);
        String bookUrl = "http://www.shuquge.com/txt/63542/index.html";
        bookUrl = "http://www.shuquge.com/txt/117367/index.html";
        header.put("referer", bookUrl);

        Document document = null;
        try {
            document = Jsoup.connect(bookUrl)
                    .headers(header)
                    .get();
        } catch (IOException e) {
            log.error("爬取书籍章节[{}]信息失败,耗时[{}],错误信息[{}]", bookUrl,
                    DateUtil.diffTime(startTime, System.currentTimeMillis()), e);

        }
        // 章节列表
        Elements listMainElement = document.getElementsByClass("listmain");
        if (listMainElement == null) {
            return;
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
                            chapter.setChapterSequence(chapterNameSplits[0].trim());
                            chapter.setChapterArticle(chapterNameSplits[1].trim());
                        }
                        bookChapterList.add(chapter);
                    }
                }
            }
        }


        System.out.println();

    }

    @org.junit.Test
    public void spiderBookPageUrlTest() throws IOException {
        String bookPageUrl = "http://www.shuquge.com/category/1_1.html";
        Map<String, String> header = new HashMap<String, String>(16);
        header.putAll(SpiderConstants.SQG_SPIDER_HEADER_MAP);

        header.put("referer", bookPageUrl);
        Document document = Jsoup.connect(bookPageUrl)
                .headers(header)
                .get();
        Elements liElements = document.select(".wrap .up .l bd ul li");
        System.out.println();
    }

}

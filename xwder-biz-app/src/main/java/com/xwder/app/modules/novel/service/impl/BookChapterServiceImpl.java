package com.xwder.app.modules.novel.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import com.xwder.app.consts.SpiderConstant;
import com.xwder.app.consts.SysConstant;
import com.xwder.app.modules.novel.entity.BookChapter;
import com.xwder.app.modules.novel.entity.BookInfo;
import com.xwder.app.modules.novel.repository.BookChapterRepository;
import com.xwder.app.modules.novel.repository.BookInfoRepository;
import com.xwder.app.modules.novel.service.intf.BookChapterService;
import com.xwder.app.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Whitelist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xwder
 * @Description: 章节服务实现类
 * @date 2020/7/7 20:31
 */
@Slf4j
@Service
public class BookChapterServiceImpl implements BookChapterService {

    @Autowired
    private BookChapterRepository bookChapterRepository;

    @Autowired
    private BookInfoRepository bookInfoRepository;

    /**
     * 根据bookId分页查询章节信息
     *
     * @param bookId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public Page<BookChapter> listBookChapterByBookId(Integer bookId, Integer pageNum, Integer pageSize, String order) {
        Sort.Direction direction = Sort.Direction.ASC;
        if (StrUtil.equalsIgnoreCase(SysConstant.order_desc, order)) {
            direction = Sort.Direction.DESC;
        }
        Sort sort = Sort.by(direction, "id");
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize, sort);
        return bookChapterRepository.findByBookId(bookId, pageable);
    }

    /**
     * 根据bookName分页查询章节信息
     *
     * @param bookName
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public Page<BookChapter> listBookChapterByBookName(String bookName, Integer pageNum, Integer pageSize) {
        List<BookInfo> bookInfoList = bookInfoRepository.findAllByBookName(bookName);
        if (CollectionUtil.isNotEmpty(bookInfoList)) {
            BookInfo bookInfo = bookInfoList.get(0);
            Sort sort = Sort.by(Sort.Direction.DESC, "id");
            Pageable pageable = PageRequest.of(pageNum - 1, pageSize, sort);
            return bookChapterRepository.findByBookId(bookInfo.getId(), pageable);
        }
        return null;
    }

    /**
     * 根据书籍编号和章节编号查询章节信息
     *
     * @param bookId
     * @param chapterId
     * @return
     */
    @Override
    public BookChapter getBookChapterByBookIdAndChapterId(Integer bookId, Integer chapterId) {
        BookChapter bookChapter = bookChapterRepository.findBookChapterByBookIdAndId(bookId, chapterId);
        return bookChapter;
    }

    /**
     * 爬取章节内容详情
     *
     * @param bookChapter
     * @return
     */
    @Override
    public BookChapter spiderChapterContent(BookChapter bookChapter) {
        Long startTime = System.currentTimeMillis();
        String sourceUrl = bookChapter.getSourceUrl();

        log.info("爬取章节[{}]信息开始", sourceUrl);
        Map<String, String> header = new HashMap<String, String>();
        header.putAll(SpiderConstant.SQG_SPIDER_HEADER_MAP);
        header.put("referer", sourceUrl);

        Document document = null;
        try {
            document = Jsoup.connect(sourceUrl)
                    .headers(header)
                    .get();
        } catch (IOException e) {
            log.error("爬取章节[{}]信息失败,耗时[{}],错误信息[{}]", sourceUrl,
                    DateUtil.diffTime(startTime, System.currentTimeMillis()), e);
            return bookChapter;
        }
        Element content = document.getElementById("content");
        Document.OutputSettings outputSettings = new Document.OutputSettings().prettyPrint(false);
        Document doc = Jsoup.parse(content.html());
        doc.outputSettings(outputSettings);
        doc.select("br").append("\\n");
        doc.select("p").prepend("\\n");
        doc.select("p").append("\\n");
        String newHtml = doc.html().replaceAll("\\\\n", "\n");
        String plainText = Jsoup.clean(newHtml, "", Whitelist.none(), outputSettings);
        String strContentTxt = StringEscapeUtils.escapeHtml4(plainText.trim());
        int chapterSize = strContentTxt.length();
        String strContent = content.html();
        strContent = strContent.replaceAll("请记住本书首发域名：www.shuquge.com。书趣阁_笔趣阁手机版阅读网址：m.shuquge.com", "")
                .replaceAll(sourceUrl, "");
        bookChapter.setChapterContent(strContent);
        bookChapter.setChapterWordSize(chapterSize);
        log.info("爬取章节[{}]信息成功结束,耗时[{}]", sourceUrl,
                DateUtil.diffTime(startTime, System.currentTimeMillis()));
        return bookChapter;
    }
}

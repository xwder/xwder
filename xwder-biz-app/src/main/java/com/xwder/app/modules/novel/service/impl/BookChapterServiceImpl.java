package com.xwder.app.modules.novel.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.xwder.app.consts.SpiderConstants;
import com.xwder.app.consts.SysConfigConstants;
import com.xwder.app.modules.novel.entity.BookChapter;
import com.xwder.app.modules.novel.entity.BookInfo;
import com.xwder.app.modules.novel.repository.BookChapterRepository;
import com.xwder.app.modules.novel.repository.BookInfoRepository;
import com.xwder.app.modules.novel.service.intf.BookChapterService;
import com.xwder.app.modules.novel.service.intf.BookInfoService;
import com.xwder.app.modules.novel.service.intf.SpiderBookChapterService;
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
import org.springframework.scheduling.annotation.Async;
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

    /**
     * 缓存后面章节信息 key: bookId_chapterId value: BookChapter
     */
    private static Map<String, BookChapter> cacheBookChapterMap = new HashMap();
    /**
     * 缓存章节数量
     */
    private Integer cacheChapterSize = 5;

    @Autowired
    private BookChapterRepository bookChapterRepository;

    @Autowired
    private BookInfoService bookInfoService;

    @Autowired
    private SpiderBookChapterService spiderBookChapterService;

    /**
     * 根据bookId查询所有章节信息
     * @param bookId 书籍id
     * @return 所有章节信息
     */
    @Override
    public List<BookChapter> findAllByBookId(Integer bookId) {
        return bookChapterRepository.findAllByBookId(bookId);
    }

    /**
     * 根据bookId查询章节信息
     * @param bookId 书籍id
     * @param pageable 分页信息
     * @return 章节信息
     */
    @Override
    public List<BookChapter> findAllByBookIdPageable(Integer bookId, Pageable pageable) {
        return bookChapterRepository.findAllByBookId(bookId, pageable);
    }

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
        if (StrUtil.equalsIgnoreCase(SysConfigConstants.order_desc, order)) {
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
    public Page<BookChapter> listBookChapterByBookName(String bookName, Integer pageNum, Integer pageSize) {
        List<BookInfo> bookInfoList = bookInfoService.findAllByBookName(bookName);
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
        // 先从缓存中读取当前章节
        String chapterCacheKey = bookId + "_" + chapterId;
        BookChapter chapter = cacheBookChapterMap.get(chapterCacheKey);
        if (chapter != null) {
            return chapter;
        } else {
            chapter = bookChapterRepository.findBookChapterByBookIdAndId(bookId, chapterId);
            if (chapter == null) {
                return null;
            }
        }
        // 获取章节内容
        // 章节内容
        String localChapterContent = bookInfoService.getLocalChapterContent(bookId, chapterId);
        if (StrUtil.isEmpty(localChapterContent)) {
            spiderChapterContent(chapter);
        } else {
            chapter.setChapterContent(localChapterContent);
        }
        return chapter;
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

        if (StrUtil.isNotEmpty(bookChapter.getChapterContent())) {
            return bookChapter;
        }

        String sourceUrl = bookChapter.getSourceUrl();

        log.info("爬取章节[{}]信息开始", sourceUrl);
        Map<String, String> header = new HashMap<String, String>();
        header.putAll(SpiderConstants.SQG_SPIDER_HEADER_MAP);
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

    /**
     * 根据书籍编号和章节编号 缓存后续章节 加快章节读取速度 缓存后续5章
     *
     * @param bookId
     * @param chapterId
     * @return
     */
    @Async
    @Override
    public void cacheBookChapter(Integer bookId, Integer chapterId) {
        int cacheChapterId = chapterId;
        for (; cacheChapterId < (chapterId + cacheChapterSize); cacheChapterId++) {
            String cacheChapterKey = bookId + "_" + cacheChapterId;
            if (cacheBookChapterMap.get(cacheChapterKey) == null) {
                BookChapter cacheChapter = getBookChapterByBookIdAndChapterId(bookId, cacheChapterId);
                cacheBookChapterMap.put(cacheChapterKey, cacheChapter);
                log.info("缓存书籍[{}] 章节 [{}] 成功", cacheChapter.getBookName(), cacheChapter.getChapterName());
            }
        }
        // 删除当前章节之前的缓存信息 往前五章后删除所有
        for (cacheChapterId = 1; cacheChapterId < chapterId - cacheChapterSize; cacheChapterId++) {
            String cacheChapterKey = bookId + "_" + cacheChapterId;
            if (cacheBookChapterMap.get(cacheChapterKey) != null) {
                cacheBookChapterMap.remove(cacheChapterKey);
            }
        }
    }

    /**
     * 根据书籍id更新数据最新章节信息
     *
     * @param bookId 书籍id
     * @return 更新状态成功失败
     * @Description: 根据书籍id更新数据最新章节信息
     */
    @Override
    @Transactional(rollbackFor = {Exception.class})
    public boolean updateBookChapterByBookId(Integer bookId) {
        BookInfo bookInfo = bookInfoService.getBookInfoById(bookId);
        if (bookInfo == null) {
            log.error("更新书籍章节失败 书籍编号[{}]不存在", bookId);
            return false;
        }
        List<BookChapter> spiderBookChapterList = spiderBookChapterService.spiderBookChapterByBookUrl(bookInfo.getBookUrl());
        if (CollectionUtil.isEmpty(spiderBookChapterList)) {
            log.error("更新书籍章节失败 书籍编号[{}] 爬取章节列表失败", bookId);
            return false;
        }

        List<BookChapter> allDbExistBookChapterList = bookChapterRepository.findAllByBookId(bookId);
        // 章节序号
        int lastChapterNo = CollectionUtil.isEmpty(allDbExistBookChapterList)?1
                :(allDbExistBookChapterList.stream().map(x->x.getChapterNo()).reduce(Integer::max).orElse(1));
        for (BookChapter spiderBookChapter : spiderBookChapterList) {
            boolean existFlag = false;
            BookChapter lastChapter = null;

            if (CollectionUtil.isNotEmpty(allDbExistBookChapterList)) {
                for (BookChapter bookChapter : allDbExistBookChapterList) {
                    if (StrUtil.equals(spiderBookChapter.getChapterName().trim(), bookChapter.getChapterName().trim())
                            && StrUtil.equals(spiderBookChapter.getSourceUrl(), bookChapter.getSourceUrl())) {
                        existFlag = true;
                        lastChapter = bookChapter;
                        break;
                    }
                }
            }

            if (!existFlag) {
                Date date = new Date();
                spiderBookChapter.setBookId(bookId);
                spiderBookChapter.setBookName(bookInfo.getBookName());
                spiderBookChapter.setAuthor(bookInfo.getAuthor());
                spiderBookChapter.setUpdateTime(date);
                spiderBookChapter.setChapterNo(lastChapterNo + 1);
                spiderBookChapter.setGmtCreate(date);
                spiderBookChapter.setGmtModified(date);
                bookChapterRepository.save(spiderBookChapter);
                lastChapterNo = lastChapterNo + 1;
            }
        }
        return true;
    }
}

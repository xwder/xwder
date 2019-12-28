package com.xwder.biz.book.service.impl;

import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.xwder.biz.book.dao.BookChapterDao;
import com.xwder.biz.book.service.intf.ChapterService;
import com.xwder.biz.model.book.BookChapter;
import com.xwder.biz.model.book.mapper.BookChapterMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 图书模块章节ServiceImpl
 *
 * @author wande
 * @version 1.0
 * @date 2019/12/27
 */
@Service
public class ChapterServiceImpl implements ChapterService {

    @Autowired
    private BookChapterDao bookChapterDao;

    @Autowired
    private BookChapterMapper bookChapterMapper;

    @HystrixCommand(fallbackMethod = "listBookChapterByBookIdFallBack")
    @Override
    public List<BookChapter> listChapterByBookId(Integer bookId, Integer withContent, Integer pageNum, Integer pageSize) {
        BookChapter bookChapter = BookChapter.builder().bookId(bookId).build();
        if (withContent > 0) {
            PageHelper.startPage(pageNum, pageSize);
            List<BookChapter> chapterList = bookChapterDao.listBookChapterByBookId(bookChapter);
            return chapterList;
        }
        PageHelper.startPage(pageNum, pageSize);
        return bookChapterDao.listBookChapterByBookIdNoContent(bookChapter);
    }

    /**
     * 服务不可用 降级回调方法
     *
     * @return
     */
    public List<BookChapter> listBookChapterByBookIdFallBack(Integer bookId, Integer withContent, Integer pageNum, Integer pageSize) {
        return Lists.newArrayList();
    }
}

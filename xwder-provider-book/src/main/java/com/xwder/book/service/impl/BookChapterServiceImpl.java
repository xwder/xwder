package com.xwder.book.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.xwder.book.dao.BookChapterDao;
import com.xwder.book.service.BookChapterService;
import com.xwder.framework.common.constan.Constant;
import com.xwder.framework.domain.book.BookChapter;
import com.xwder.framework.utils.page.PageData;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import tk.mybatis.mapper.entity.Example;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: xwder
 * @Date: 2019/7/15 23:52
 * @Description:
 */
@Service
public class BookChapterServiceImpl implements BookChapterService {


    @Autowired
    private BookChapterDao bookChapterDao;

    @Override
    public PageData listBookChapter(Integer bookId,Integer pageNum, Integer pageSize, String sortField, Sort.Direction order) {

        if (pageNum == null) {
            pageNum = Constant.DEFAULT_PAGE_NUM;
        }
        if (pageSize == null) {
            pageSize = Constant.DEFAULT_PAGE_SIZE;
        }
        Pageable pageable;

        if (StringUtils.isNotEmpty(sortField) && StringUtils.isNotEmpty(order.name())) {
            PageHelper.startPage(pageNum, pageSize, sortField + "  " + order);
        } else {
            PageHelper.startPage(pageNum, pageSize);
        }


        Example example = new Example(BookChapter.class);
        example.createCriteria().andEqualTo("bookId",bookId);
        List<BookChapter> bookChapterList = bookChapterDao.selectByExample(example);

        PageInfo<BookChapter> pageInfo = new PageInfo<>(bookChapterList);
        long total = pageInfo.getTotal();
        int pages = pageInfo.getPages();
        bookChapterList = pageInfo.getList();

        return PageData.builder()
                .pageNum(pageNum)
                .pageSize(pageSize)
                .total((int) total)
                .totalPages(pages)
                .data(bookChapterList)
                .build();
    }

    @HystrixCommand(fallbackMethod = "listBookChapterByBookIdFallBack")
    @Override
    public PageData listBookChapterByBookId(Integer bookId, Integer pageNum, Integer pageSize, String sortField, Sort.Direction order) {

        if (pageNum == null) {
            pageNum = Constant.DEFAULT_PAGE_NUM;
        }
        if (pageSize == null) {
            pageSize = Constant.DEFAULT_PAGE_SIZE;
        }
        Pageable pageable;

        if (StringUtils.isNotEmpty(sortField) && StringUtils.isNotEmpty(order.name())) {
            PageHelper.startPage(pageNum, pageSize, sortField + "  " + order);
        } else {
            PageHelper.startPage(pageNum, pageSize);
        }

        BookChapter bookChapter = BookChapter.builder().bookId(bookId).build();
        List<BookChapter> bookChapterList = bookChapterDao.listBookChapterByBookId(bookChapter);
        PageInfo<BookChapter> pageInfo = new PageInfo<>(bookChapterList);
        long total = pageInfo.getTotal();
        int pages = pageInfo.getPages();
        bookChapterList = pageInfo.getList();

        return PageData.builder()
                .pageNum(pageNum)
                .pageSize(pageSize)
                .total((int) total)
                .totalPages(pages)
                .data(bookChapterList)
                .build();

    }


    /**
     * 服务不可用 降级回调方法
     * @return
     */
    public PageData listBookChapterByBookIdFallBack(Integer bookId, Integer pageNum, Integer pageSize, String sortField, Sort.Direction order) {
        return PageData.builder()
                .pageNum(pageNum)
                .pageSize(pageSize)
                .total(0)
                .totalPages(0)
                .data(null)
                .build();
    }


}

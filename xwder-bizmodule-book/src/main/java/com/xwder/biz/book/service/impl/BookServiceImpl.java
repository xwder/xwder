package com.xwder.biz.book.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.xwder.biz.book.dao.BookInfoDao;
import com.xwder.biz.book.service.intf.BookService;
import com.xwder.biz.model.book.BookInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: xwder
 * @Date: 2019/12/26
 * @Description:
 */
@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookInfoDao bookInfoDao;

    @HystrixCommand(fallbackMethod = "listBookInfoFallBack")
    @Override
    public List<BookInfo> listBookInfo(Integer pageNum, Integer pageSize) {
        Page<BookInfo> page = PageHelper.startPage(pageNum, pageSize);
        List<BookInfo> bookInfoList = bookInfoDao.selectAll();
        return bookInfoList;
    }

    /**
     * 服务不可用 降级回调方法
     *
     * @return
     */
    public List<BookInfo> listBookInfoFallBack(Integer pageNum, Integer pageSize) {
        return Lists.newArrayList();
    }

}

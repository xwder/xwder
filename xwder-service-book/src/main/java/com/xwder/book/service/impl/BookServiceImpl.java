package com.xwder.book.service.impl;

import com.github.pagehelper.PageHelper;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.xwder.book.dao.BookInfoDao;
import com.xwder.book.service.BookService;
import com.xwder.common.constan.Constant;
import com.xwder.framework.domain.book.BookInfo;
import com.xwder.framework.utils.page.PageData;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: xwder
 * @Date: 2019/7/15 23:20
 * @Description:
 */
@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookInfoDao bookInfoDao;

    @HystrixCommand(fallbackMethod = "listBookInfoFallBack")
    @Override
    public PageData listBookInfo(Integer pageNum, Integer pageSize, String sortField, Sort.Direction order) {

        if (pageNum == null) {
            pageNum = Constant.DEFAULT_PAGE_NUM;
        }
        if (pageSize == null) {
            pageSize = Constant.DEFAULT_PAGE_SIZE;
        }
        Pageable pageable;

        if (StringUtils.isNotEmpty(sortField) && StringUtils.isNotEmpty(order.name())) {
            PageHelper.startPage(pageNum, pageSize, sortField + "  " + order);
        }else{
            PageHelper.startPage(pageNum, pageSize);
        }

        List<BookInfo> bookInfoList = bookInfoDao.selectAll();
        com.github.pagehelper.PageInfo<BookInfo> pageInfo = new com.github.pagehelper.PageInfo<BookInfo>(bookInfoList);
        long total = pageInfo.getTotal();
        int pages = pageInfo.getPages();
        bookInfoList = pageInfo.getList();

        return PageData.builder()
                .pageNum(pageNum)
                .pageSize(pageSize)
                .total((int) total)
                .totalPages(pages)
                .data(bookInfoList)
                .build();
    }
    /**
     * 服务不可用 降级回调方法
     * @return
     */
    public PageData listBookInfoFallBack(Integer pageNum, Integer pageSize, String sortField, Sort.Direction order){
        PageData pageInfo = PageData.builder().total(0).data(null).build();
        return pageInfo;
    }


}

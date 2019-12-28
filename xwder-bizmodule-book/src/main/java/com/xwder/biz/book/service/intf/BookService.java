package com.xwder.biz.book.service.intf;

import com.xwder.biz.model.book.BookInfo;

import java.util.List;

/**
 * @Author: xwder
 * @Date: 2019/12/26
 * @Description:
 */
public interface BookService {


    /**
     * 分页查询
     *
     * @param pageNum  页码
     * @param pageSize 页数
     * @return CommonResult
     */
    List<BookInfo> listBookInfo(Integer pageNum, Integer pageSize);
}

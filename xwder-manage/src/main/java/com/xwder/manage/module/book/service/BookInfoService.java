package com.xwder.manage.module.book.service;

import com.xwder.framework.utils.page.PageData;
import org.springframework.data.domain.Sort;

/**
 * @Author: xwder
 * @Date: 2019/7/16 01:06
 * @Description:
 */
public interface BookInfoService {

    /**
     * 分页查询书籍信息
     * @param pageNum
     * @param pageSize
     * @param sortField
     * @param order
     * @return
     */
    public PageData listBookInfo(Integer pageNum, Integer pageSize, String sortField, Sort.Direction order);
}

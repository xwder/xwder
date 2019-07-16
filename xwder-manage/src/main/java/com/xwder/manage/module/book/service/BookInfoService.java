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
     * 查询所有的小说
     *
     * @return
     */
    public PageData findAll(Integer pageNum, Integer pageSize, String sortField, Sort.Direction order);
}

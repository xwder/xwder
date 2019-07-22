package com.xwder.manage.module.book.service;

import com.xwder.framework.utils.page.PageData;
import org.springframework.data.domain.Sort;

/**
 * @Author: xwder
 * @Date: 2019/7/16 01:25
 * @Description:
 */
public interface BookChapterService {
    /**
     * 查询所有的章节
     *
     * @return
     */
    public PageData findAll(Integer bookId,Integer pageNum, Integer pageSize, String sortField, Sort.Direction order);
}


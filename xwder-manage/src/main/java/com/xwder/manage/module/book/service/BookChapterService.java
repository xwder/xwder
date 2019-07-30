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
     * 根据书籍id分页查询章节信息
     *
     * @param bookId
     * @param pageNum
     * @param pageSize
     * @param sortField
     * @param order
     * @return
     */
    public PageData listBookChapterByPage(Integer bookId, Integer pageNum, Integer pageSize, String sortField, Sort.Direction order);
}


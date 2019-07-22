package com.xwder.book.service;

import com.xwder.framework.utils.page.PageData;
import org.springframework.data.domain.Sort;

/**
 * @Author: xwder
 * @Date: 2019/7/15 23:52
 * @Description:
 */
public interface BookChapterService {

    /**
     * 分页查询
     *
     * @param bookId   book id
     * @param pageNum   页码
     * @param pageSize  页数
     * @param sortField 排序字段
     * @param order     顺序
     * @return PageResultEntity
     */
    PageData findAll(Integer bookId,Integer pageNum, Integer pageSize, String sortField, Sort.Direction order);

}

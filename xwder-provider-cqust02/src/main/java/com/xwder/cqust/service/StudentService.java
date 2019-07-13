package com.xwder.cqust.service;

import com.xwder.framework.domain.cqust.KyStudent;
import com.xwder.framework.utils.page.PageInfo;
import org.springframework.data.domain.Sort;

import java.util.List;

/**
 * @Author: xwder
 * @Date: 2019/7/10 23:56
 * @Description:
 */
public interface StudentService {

    /**
     * 分页查询
     *
     * @param pageNum   页码
     * @param pageSize  页数
     * @param sortField 排序字段
     * @param order     顺序
     * @return PageResultEntity
     */
    PageInfo findAll(Integer pageNum, Integer pageSize, String sortField, Sort.Direction order);
}

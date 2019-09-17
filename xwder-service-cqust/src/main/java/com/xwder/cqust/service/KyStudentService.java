package com.xwder.cqust.service;

import com.xwder.framework.utils.page.PageData;
import org.springframework.data.domain.Sort;

/**
 * @Author: xwder
 * @Date: 2019/7/16 00:34
 * @Description:
 */
public interface KyStudentService {

    /**
     * 分页查询
     *
     * @param pageNum   页码
     * @param pageSize  页数
     * @param sortField 排序字段
     * @param order     顺序
     * @return PageResultEntity
     */
    PageData listStudent(Integer pageNum, Integer pageSize, String sortField, Sort.Direction order);

}

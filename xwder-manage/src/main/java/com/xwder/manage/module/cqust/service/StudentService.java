package com.xwder.manage.module.cqust.service;

import com.xwder.framework.utils.page.PageData;
import org.springframework.data.domain.Sort;

/**
 * @Author: xwder
 * @Date: 2019/7/10 23:56
 * @Description:
 */
public interface StudentService {

    /**
     * 查询所有的学生
     *
     * @return
     */
    public PageData listStudent(Integer pageNum, Integer pageSize, String sortField, Sort.Direction order);
}

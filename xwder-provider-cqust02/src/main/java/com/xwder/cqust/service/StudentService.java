package com.xwder.cqust.service;

import com.xwder.cqust.domain.KyStudent;

import java.util.List;

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
    List<KyStudent> listKyStudent();

    /**
     * 分页查询
     *
     * @param pageNo   页码
     * @param pageSize 页数
     * @return PageResultEntity
     */
    List<KyStudent> findAll(Integer pageNo, Integer pageSize);
}

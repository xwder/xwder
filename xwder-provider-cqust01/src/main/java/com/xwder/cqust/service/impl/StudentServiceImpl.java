package com.xwder.cqust.service.impl;

import com.google.common.collect.Lists;
import com.xwder.cqust.domain.KyStudent;
import com.xwder.cqust.repository.KyStudentRepository;
import com.xwder.cqust.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: xwder
 * @Date: 2019/7/10 23:57
 * @Description:
 */
@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private KyStudentRepository kyStudentRepository;


    @Override
    public List<KyStudent> listKyStudent() {
        Iterable<KyStudent> iterable = kyStudentRepository.findAll();
        List<KyStudent> list = Lists.newArrayList(iterable);
        return list;
    }

    @Override
    public List<KyStudent> findAll(Integer pageNo, Integer pageSize) {

        if (pageNo == null) {
            pageNo = 1;
        }
        if (pageSize == null) {
            pageSize = 20;
        }

        Sort sort = new Sort(Sort.Direction.ASC, "id");
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<KyStudent> datas = kyStudentRepository.findAll(pageable);
        //总条数
        int total = (int) datas.getTotalElements();
        //总页数
        int totalPages = datas.getTotalPages();
        //数据列表
        List<KyStudent> list = datas.getContent();

        return list;
    }
}

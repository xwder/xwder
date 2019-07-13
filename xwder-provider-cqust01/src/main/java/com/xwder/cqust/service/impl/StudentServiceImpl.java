package com.xwder.cqust.service.impl;

import com.xwder.cqust.repository.KyStudentRepository;
import com.xwder.cqust.service.StudentService;
import com.xwder.framework.common.constan.Constant;
import com.xwder.framework.domain.cqust.KyStudent;
import com.xwder.framework.utils.page.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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
    public PageInfo findAll(Integer pageNum, Integer pageSize, String sortField, Sort.Direction order) {

        if (pageNum == null) {
            pageNum = Constant.DEFAULT_PAGE_NUM;
        }
        if (pageSize == null) {
            pageSize = Constant.DEFAULT_PAGE_SIZE;
        }
        Pageable pageable;
        if (StringUtils.isNotEmpty(sortField) && StringUtils.isNotEmpty(order.name())) {
            Sort sort = new Sort(Sort.Direction.ASC, "id");
            pageable = PageRequest.of(pageNum, pageSize, sort);
        } else {
            pageable = PageRequest.of(pageNum, pageSize);
        }

        Page<KyStudent> datas = kyStudentRepository.findAll(pageable);

        return PageInfo.builder()
                .pageNum(pageNum)
                .pageSize(pageSize)
                .total((int) datas.getTotalElements())
                .totalPages(datas.getTotalPages())
                .data(datas.getContent())
                .build();
    }
}

package com.xwder.cqust.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xwder.cqust.dao.KyStudentDao;
import com.xwder.cqust.service.KyStudentService;
import com.xwder.framework.common.constan.Constant;
import com.xwder.framework.domain.cqust.KyStudent;
import com.xwder.framework.utils.page.PageData;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: xwder
 * @Date: 2019/7/16 00:34
 * @Description:
 */
@Service
public class KyStudentServiceImpl implements KyStudentService {

    @Autowired
    private KyStudentDao kyStudentDao;

    @Override
    public PageData findAll(Integer pageNum, Integer pageSize, String sortField, Sort.Direction order) {

        if (pageNum == null) {
            pageNum = Constant.DEFAULT_PAGE_NUM;
        }
        if (pageSize == null) {
            pageSize = Constant.DEFAULT_PAGE_SIZE;
        }
        Pageable pageable;

        if (StringUtils.isNotEmpty(sortField) && StringUtils.isNotEmpty(order.name())) {
            PageHelper.startPage(pageNum, pageSize, sortField + "  " + order);
        } else {
            PageHelper.startPage(pageNum, pageSize);
        }

        List<KyStudent> studentList = kyStudentDao.selectAll();
        PageInfo<KyStudent> pageInfo = new PageInfo<KyStudent>(studentList);
        long total = pageInfo.getTotal();
        int pages = pageInfo.getPages();
        studentList = pageInfo.getList();

        return PageData.builder()
                .pageNum(pageNum)
                .pageSize(pageSize)
                .total((int) total)
                .totalPages(pages)
                .data(studentList)
                .build();
    }
}

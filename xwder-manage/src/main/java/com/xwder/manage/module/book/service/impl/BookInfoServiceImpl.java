package com.xwder.manage.module.book.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.xwder.api.book.BookInfoServiceApi;
import com.xwder.framework.common.constan.Constant;
import com.xwder.framework.domain.book.BookInfo;
import com.xwder.framework.utils.message.Result;
import com.xwder.framework.utils.page.PageData;
import com.xwder.manage.module.book.service.BookInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: xwder
 * @Date: 2019/7/16 01:07
 * @Description:
 */
@Service
public class BookInfoServiceImpl implements BookInfoService {


    @Autowired
    private BookInfoServiceApi bookInfoServiceApi;

    @Override
    public PageData findAll(Integer pageNum, Integer pageSize, String sortField, Sort.Direction order) {

        if (pageNum == null) {
            pageNum = Constant.DEFAULT_PAGE_NUM;
        }
        if (pageSize == null) {
            pageSize = Constant.DEFAULT_PAGE_SIZE;
        }

        Result<BookInfo> result = bookInfoServiceApi.listBookByPage(pageNum, pageSize, null, null);

        LinkedHashMap linkedHashMap = (LinkedHashMap) result.getData();
        int total = (int) linkedHashMap.get("total");

        ArrayList<Map> arrayList = (ArrayList<Map>) linkedHashMap.get("data");

        List<BookInfo> list = new ArrayList<>();

        for (Map map : arrayList) {
            JSONObject jsonObject = new JSONObject(map);
            BookInfo bookInfo = jsonObject.toJavaObject(BookInfo.class);
            list.add(bookInfo);
        }
        PageData pageInfo = PageData.builder().total(total).data(list).build();
        return pageInfo;
    }
}

package com.xwder.manage.module.book.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.xwder.api.book.BookInfoServiceApi;
import com.xwder.common.constan.Constant;
import com.xwder.framework.domain.book.BookChapter;
import com.xwder.framework.utils.message.Result;
import com.xwder.framework.utils.page.PageData;
import com.xwder.manage.module.book.service.BookChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Author: xwder
 * @Date: 2019/7/16 01:27
 * @Description:
 */
@Service
public class BookChapterServiceImpl implements BookChapterService {

    @Autowired
    private BookInfoServiceApi bookChapterServiceApi;

    @Override
    public PageData listBookChapterByPage(Integer bookId, Integer pageNum, Integer pageSize, String sortField, Sort.Direction order) {
        if (pageNum == null) {
            pageNum = Constant.DEFAULT_PAGE_NUM;
        }
        if (pageSize == null) {
            pageSize = Constant.DEFAULT_PAGE_SIZE;
        }

        Result<BookChapter> result = bookChapterServiceApi.listBookChapterByPage(bookId, pageNum, pageSize, null, null);

        LinkedHashMap linkedHashMap = (LinkedHashMap) result.getData();
        int total = (int) linkedHashMap.get("total");

        ArrayList<Map> arrayList = (ArrayList<Map>) linkedHashMap.get("data");

        List<BookChapter> list = new ArrayList<>();

        for (Map map : arrayList) {
            JSONObject jsonObject = new JSONObject(map);
            BookChapter bookChapter = jsonObject.toJavaObject(BookChapter.class);
            list.add(bookChapter);
        }
        PageData pageInfo = PageData.builder().total(total).data(list).build();
        return pageInfo;
    }
}

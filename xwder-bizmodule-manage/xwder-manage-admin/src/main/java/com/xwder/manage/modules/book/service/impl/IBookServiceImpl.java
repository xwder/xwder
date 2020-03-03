package com.xwder.manage.modules.book.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.xwder.manage.common.core.page.TableDataInfo;
import com.xwder.manage.modules.book.config.BookServiceUrlConfig;
import com.xwder.manage.modules.book.dto.BookInfoDto;
import com.xwder.manage.modules.book.service.intf.IBookService;
import com.xwder.manage.utils.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author wande
 * @version 1.0
 * @date 2019/12/30
 */
@Slf4j
@Service
public class IBookServiceImpl implements IBookService {

    @Autowired
    private BookServiceUrlConfig bookServiceUrlConfig;

    @Override
    public TableDataInfo listBookInfo(int PageNum, int pageSize, BookInfoDto bookInfoDto) {
        String url = bookServiceUrlConfig.getGatewayUrl() +
                bookServiceUrlConfig.getListBook() +
                "?PageNum=" +
                PageNum +
                "&pageSize=" +
                pageSize;
        String result = null;
        try {
            result = HttpClientUtil.doGet(url);
        } catch (IOException e) {
            log.error("获取书籍列表失败,请求书籍服务的url [{}], 异常信息 []", url, e);
        }
        Map map = JSON.parseObject(result, Map.class);
        if (map != null && (int) map.get("code") == 200) {
            Map data = (Map) map.get("data");
            TableDataInfo tableDataInfo = TableDataInfo.builder()
                    .rows((List<BookInfoDto>) data.get("list"))
                    .total((long) ((int) data.get("total")))
                    .code(0)
                    .build();
            return tableDataInfo;
        }

        return TableDataInfo.builder()
                .code(500)
                .rows(Lists.newArrayList())
                .build();
    }

}


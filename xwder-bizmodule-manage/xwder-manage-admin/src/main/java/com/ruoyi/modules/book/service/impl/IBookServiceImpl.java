package com.ruoyi.modules.book.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.modules.book.config.BookServiceUrlConfig;
import com.ruoyi.modules.book.dto.BookInfoDto;
import com.ruoyi.modules.book.service.intf.IBookService;
import com.ruoyi.utils.HttpClientUtil;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author wande
 * @version 1.0
 * @date 2019/12/30
 */
@Service
public class IBookServiceImpl implements IBookService {

    @Autowired
    private BookServiceUrlConfig bookServiceUrlConfig;

    @Override
    public TableDataInfo listBookInfo(int PageNum, int pageSize, BookInfoDto bookInfoDto) {
        String url = new StringBuilder().append(bookServiceUrlConfig.getServiceBookGatewayUrl())
                .append(bookServiceUrlConfig.getListBook())
                .append("?PageNum=")
                .append(PageNum)
                .append("&pageSize=")
                .append(pageSize)
                .toString();
        String result = HttpClientUtil.doGet(url);
        Map map = JSON.parseObject(result, Map.class);
        if ((int) map.get("code") == 200) {
            Map data = (Map) map.get("data");
            TableDataInfo tableDataInfo = TableDataInfo.builder()
                    .rows((List<BookInfoDto>) data.get("list"))
                    // TODO bug toString
                    .total(Long.parseLong(data.get("total").toString()))
                    .code((Integer) map.get("code"))
                    .build();
            return tableDataInfo;
        }

        return TableDataInfo.builder()
                .code(500)
                .rows(Lists.newArrayList())
                .build();
    }
}

package com.xwder.biz.book.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.xwder.biz.book.config.BookSpiderUrlConfig;
import com.xwder.biz.book.dao.BookChapterDao;
import com.xwder.biz.book.service.intf.ChapterService;
import com.xwder.biz.model.book.BookChapter;
import com.xwder.biz.model.book.mapper.BookChapterMapper;
import com.xwder.cloud.commmon.api.CommonResult;
import com.xwder.cloud.commmon.api.ResultCode;
import com.xwder.cloud.commmon.utils.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 图书模块章节ServiceImpl
 *
 * @author wande
 * @version 1.0
 * @date 2019/12/27
 */
@Slf4j
@Service
public class ChapterServiceImpl implements ChapterService {

    @Autowired
    private BookChapterDao bookChapterDao;

    @Autowired
    private BookChapterMapper bookChapterMapper;

    @Autowired
    private BookSpiderUrlConfig bookSpiderUrlConfig;

    @HystrixCommand(fallbackMethod = "listBookChapterByBookIdFallBack")
    @Override
    public List<BookChapter> listChapterByBookId(Integer bookId, Integer withContent, Integer pageNum, Integer pageSize) {
        BookChapter bookChapter = BookChapter.builder().bookId(bookId).build();
        if (withContent > 0) {
            PageHelper.startPage(pageNum, pageSize);
            List<BookChapter> chapterList = bookChapterDao.listBookChapterByBookId(bookChapter);
            return chapterList;
        }
        PageHelper.startPage(pageNum, pageSize);
        return bookChapterDao.listBookChapterByBookIdNoContent(bookChapter);
    }

    /**
     * 服务不可用 降级回调方法
     *
     * @return
     */
    public List<BookChapter> listBookChapterByBookIdFallBack(Integer bookId, Integer withContent, Integer pageNum, Integer pageSize) {
        return Lists.newArrayList();
    }

    @Override
    public CommonResult getLatestChapters(String author, String bookName, String bookUrl) {
        String url = new StringBuffer()
                .append(bookSpiderUrlConfig.getBookLatestChapterUrl())
                .append("?bookUrl=")
                .append(bookUrl)
                .append("&author=")
                .append("&bookName=")
                .append(bookName)
                .toString();

        String json = HttpClientUtil.doGet(url);
        JSONObject jsonObject = JSONObject.parseObject(json);
        Integer code = (Integer) jsonObject.get("code");
        String msg = (String) jsonObject.get("msg");
        if (code != ResultCode.SUCCESS.getCode()) {
            log.info("请求spider获取 [{}]-[{}]-[{}] 最新章节信息失败，失败信息{}", author, bookName, bookUrl, msg);
            return CommonResult.failed(code, msg);
        }
        if (code == 200) {
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            List<Map> list = new ArrayList<>();
            if (jsonArray.size() == 0) {
                log.info("请求spider获取 [{}]-[{}]-[{}] 最新章节信息成功，信息{}", author, bookName, bookUrl, msg);
                return CommonResult.success(Lists.newArrayList(), msg);
            }
            int size = jsonArray.size();
            int i = 0;
            for (; i < size; i++) {
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                author = (String) jsonObject1.get("author");
                String bookId = (String) jsonObject1.get("bookId");
                String chapterContent = (String) jsonObject1.get("chapterContent");
                bookName = (String) jsonObject1.get("bookName");
                String chapterName = (String) jsonObject1.get("chapterName");
                String sourceUrl = (String) jsonObject1.get("sourceUrl");
                Date updateTime = (Date) jsonObject1.get("updateTime");
                Integer chapterWorkSize = (Integer) jsonObject1.get("chapterWorkSize");
                Map map = Maps.newHashMap();
                map.put("author", author);
                map.put("bookId", bookId);
                map.put("chapterContent", chapterContent);
                map.put("bookName", bookName);
                map.put("chapterName", chapterName);
                map.put("sourceUrl", sourceUrl);
                map.put("updateTime", updateTime);
                map.put("chapterWorkSize", chapterWorkSize);
                list.add(map);
            }
            return CommonResult.success(list);
        }
        return CommonResult.failed();
    }
}

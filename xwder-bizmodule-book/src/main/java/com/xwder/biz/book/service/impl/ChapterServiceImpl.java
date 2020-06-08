package com.xwder.biz.book.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.xwder.biz.book.config.BookSpiderUrlConfig;
import com.xwder.biz.book.dao.BookChapterCustomerMapper;
import com.xwder.biz.book.service.intf.ChapterService;
import com.xwder.biz.model.book.BookChapter;
import com.xwder.biz.model.book.mapper.BookChapterMapper;
import com.xwder.biz.model.book.mapper.BookInfoMapper;
import com.xwder.cloud.commmon.api.CommonResult;
import com.xwder.cloud.commmon.api.ResultCode;
import com.xwder.cloud.commmon.utils.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Whitelist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;

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
    private BookChapterCustomerMapper bookChapterCustomerMapper;

    @Autowired
    private BookChapterMapper bookChapterMapper;

    @Autowired
    private BookSpiderUrlConfig bookSpiderUrlConfig;

    /**
     * 任务执行线程池
     */
    protected ExecutorService executorService =
            new ThreadPoolExecutor(5,
                    10, 20L,
                    TimeUnit.SECONDS,
                    new LinkedBlockingQueue<>(2000));

    @HystrixCommand(fallbackMethod = "listBookChapterByBookIdFallBack")
    @Override
    public List<BookChapter> listChapterByBookId(Integer bookId, Integer withContent, Integer pageNum, Integer pageSize) {
        BookChapter bookChapter = BookChapter.builder().bookId(bookId).build();
        if (withContent > 0) {
            PageHelper.startPage(pageNum, pageSize);
            List<BookChapter> chapterList = bookChapterCustomerMapper.listBookChapterByBookId(bookChapter);
            return chapterList;
        }
        PageHelper.startPage(pageNum, pageSize);
        return bookChapterCustomerMapper.listBookChapterByBookIdNoContent(bookChapter);
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
                Map map = Maps.newHashMap();
                map.put("author", jsonObject1.get("author"));
                map.put("bookId", jsonObject1.get("bookId"));
                map.put("chapterContent", jsonObject1.get("chapterContent"));
                map.put("bookName", jsonObject1.get("bookName"));
                map.put("chapterName", jsonObject1.get("chapterName"));
                map.put("sourceUrl", jsonObject1.get("sourceUrl"));
                map.put("updateTime", jsonObject1.get("updateTime"));
                map.put("chapterWorkSize", jsonObject1.get("chapterWorkSize"));
                list.add(map);
            }
            return CommonResult.success(list);
        }
        return CommonResult.failed();
    }

    @Override
    public List<BookChapter> listNoContentChapter(@NotNull BookChapter bookChapter, Integer pageNum, Integer pageSize) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("bookName", bookChapter.getBookName());
        map.put("author", bookChapter.getAuthor());
        map.put("sourceUrl !=", bookChapter.getSourceUrl());
        PageHelper.startPage(pageNum, pageSize);
        List<BookChapter> bookChapters = bookChapterCustomerMapper.listBookChapterNoContent(map);
        return bookChapters;
    }

    @Override
    public void spiderChapterInfoConcurrent() {
        BookChapter queryBookChapter = new BookChapter();
        List<BookChapter> bookChapters = listNoContentChapter(queryBookChapter, 1, 100);
        List<CompletableFuture> futureList = new ArrayList<>();
        for (BookChapter bookChapter : bookChapters) {
            //此处采用多线程打包
            futureList.add(CompletableFuture.runAsync(() -> {
                try {
                    // 爬取内容保存数据
                    spiderChapterInfo(bookChapter);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println(e);
                }
            }, executorService));
        }
        // 等待所有包都打完
        if (futureList.isEmpty()) {
            return;
        }
        CompletableFuture
                .allOf(futureList.toArray(new CompletableFuture[futureList.size()]))
                .join();
    }

    @Override
    public void spiderChapterInfo(BookChapter bookChapter) {
        Map<String, String> header = new HashMap<String, String>();
        header.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
        header.put("Accept-Language", "zh-cn,zh;q=0.5");
        header.put("Accept-Charset", "  GB2312,utf-8;q=0.7,*;q=0.7");
        header.put("Connection", "keep-alive");
        //header.put("cookie", "acw_tc=2760820d15766480375335721edd64371fcb400d47561f8896c6e28c7d3b40; uuid_tt_dd=10_9926153920-1576648097988-182265; dc_session_id=10_1576648097988.363322; Hm_lvt_6bcd52f51e9b3dce32bec4a3997715ac=1576644694,1576648099; Hm_ct_6bcd52f51e9b3dce32bec4a3997715ac=6525*1*10_9926153920-1576648097988-182265; announcement=%257B%2522isLogin%2522%253Afalse%252C%2522announcementUrl%2522%253A%2522https%253A%252F%252Fblogdev.blog.csdn.net%252Farticle%252Fdetails%252F103053996%2522%252C%2522announcementCount%2522%253A0%252C%2522announcementExpire%2522%253A3600000%257D; dc_tos=q2p1ir; Hm_lpvt_6bcd52f51e9b3dce32bec4a3997715ac=1576648180; c-login-auto=3; acw_sc__v2=5df9bf415fd91f48063efe6c7f23be98ca36f37c");
        header.put("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.88 Safari/537.36");
        header.put("referer", bookChapter.getSourceUrl());

        Document document = null;
        try {
            document = Jsoup.connect(bookChapter.getSourceUrl())
                    .headers(header)
                    .get();
        } catch (IOException e) {
            return;
        }
        Element content = document.getElementById("content");
        Document.OutputSettings outputSettings = new Document.OutputSettings().prettyPrint(false);
        Document doc = Jsoup.parse(content.html());
        doc.outputSettings(outputSettings);
        doc.select("br").append("\\n");
        doc.select("p").prepend("\\n");
        doc.select("p").append("\\n");
        String newHtml = doc.html().replaceAll("\\\\n", "\n");
        String plainText = Jsoup.clean(newHtml, "", Whitelist.none(), outputSettings);
        String strContentTxt = StringEscapeUtils.unescapeHtml(plainText.trim());

        int chapterSize = strContentTxt.length();
        String strContent = content.html();

        BookChapter updateBookChapter = new BookChapter();
        updateBookChapter.setId(bookChapter.getId());
        updateBookChapter.setChapterContent(strContent);
        updateBookChapter.setChapterWordSize(chapterSize);
        Date currentDate = new Date();
        updateBookChapter.setUpdateTime(currentDate);
        updateBookChapter.setGmtModified(currentDate);

        bookChapterMapper.updateByPrimaryKey(updateBookChapter);
        log.info("[{}] -- [{}] -- [{}] 保存成功", bookChapter.getId(), bookChapter.getBookName(), chapterSize);
    }
}

package com.xwder.app.modules.blog.task;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.google.common.collect.Lists;
import com.xwder.app.modules.blog.service.intf.ArticleService;
import com.xwder.app.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.sql.Struct;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 博客 定时推送到百度
 *
 * @author xwder
 * @version 1.0
 * @date 2020-11-08 17:47
 */
@Slf4j
@Component(value = "blogPushBaiduTask")
public class BlogUrlPushBaiduTask {

    private String blogUlr = "https://www.xwder.com/blog/article/%s.html";

    // 在搜索资源平台验证的站点，比如www.example.com
    private String baidu_site_www = "https://www.xwder.com/";
    // 在搜索资源平台申请的推送用的准入密钥 www.xwder.com
    private String baidu_site_www_push_token = "3Kb7rJMKSxP2kCcE";
    // 百度推送请求url
    private String baiduPushUrl = "http://data.zz.baidu.com/urls?site=%s&token=%s";

    @Autowired
    private ArticleService articleService;


    /**
     * 查出所有需要推送的博客文章id，然后拼接完整 博客文章链接 然后推送百度
     */
    public void pushBlogUrlToBaiduService() {
        List<BigInteger> idList = articleService.listAllBlogArticleId();

        String pushUrl = String.format(baiduPushUrl, baidu_site_www, baidu_site_www_push_token);
        List urlList = pushBlogUrlToBaiduByBlogId(idList);
        String body = StrUtil.join("\n", urlList);
        String result = HttpRequest.post(pushUrl).body(body).execute().body();

        JSONObject jsonObject = JSONUtil.parseObj(result);
        Integer pushSuccessNums = jsonObject.getInt("success");
        if (pushSuccessNums != null && pushSuccessNums == urlList.size()) {
            Integer remain = jsonObject.getInt("remain");
            log.info("推送博客文章链接到百度成功，推送成功数量: {},当日剩余推送数量: {}, 推送时间: {}, 响应信息: {}",
                    pushSuccessNums, remain, DateUtil.formatDate(new Date(), DateUtil.format_yyyy_MM_dd_HHmmss), result);
            return;
        }
        log.info("推送博客文章链接到百度失败,响应信息{}", result);
    }

    /**
     * 根据博客文章ID 生成博客文章url
     */
    private List pushBlogUrlToBaiduByBlogId(List<BigInteger> idList) {
        ArrayList urlList = Lists.newArrayList();
        for (BigInteger id : idList) {
            urlList.add(String.format(blogUlr, id.intValue()));
        }
        return urlList;
    }

}

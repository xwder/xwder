package com.xwder.app.sysmodules.blog.controller;

import com.xwder.app.common.result.CommonResult;
import com.xwder.app.modules.blog.service.intf.ArticleService;
import com.xwder.app.sysmodules.blog.dto.ArticleDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 博客文章controller
 *
 * @author wande
 * @version 1.0
 * @date 2020/09/22
 */
@Controller
@Service(value = "sysArticleController")
@RequestMapping("/sys/blog")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    /**
     * 博客文章列表页面
     *
     * @return
     */
    @RequestMapping(value = "/articleListPage")
    public String listArticlePage() {
        return "sys/blog/articleList";
    }

    /**
     * 分页查询 博客文章
     *
     * @param articleDto
     * @return
     */
    @RequestMapping("/articleList")
    @ResponseBody
    public CommonResult listArticle(ArticleDto articleDto) {
        Page pageData = articleService.listArticlePageData(articleDto);
        return CommonResult.success(pageData);
    }
}

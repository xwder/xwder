package com.xwder.app.sysmodules.blog.controller;

import com.xwder.app.common.result.CommonResult;
import com.xwder.app.consts.SysConfigConstants;
import com.xwder.app.modules.blog.entity.Article;
import com.xwder.app.modules.blog.service.intf.ArticleService;
import com.xwder.app.modules.blog.service.intf.CategoryService;
import com.xwder.app.modules.user.entity.User;
import com.xwder.app.sysmodules.blog.dto.ArticleDto;
import com.xwder.app.utils.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

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

    @Autowired
    private CategoryService categoryService;

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
     * 博客文编辑页面
     *
     * @return
     */
    @RequestMapping(value = "/articleEdit")
    public String articleEdit(@RequestParam(required = false) Long id, Model model) {
        if (id != null) {
            Article article = articleService.getArticleById(id);
            model.addAttribute("article", article);
        }
        User sessionUser = (User) SessionUtil.getSessionAttribute(SysConfigConstants.SESSION_USER);
        // 查询用户所有的博客分类
        List categoryList = categoryService.listCategoryByUserId(sessionUser.getId());
        model.addAttribute("categoryList", categoryList);

        return "sys/blog/articleEdit";
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

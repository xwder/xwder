package com.xwder.app.modules.blog.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.google.common.base.Joiner;
import com.xwder.app.consts.SysConstant;
import com.xwder.app.modules.blog.entity.Article;
import com.xwder.app.modules.blog.entity.Category;
import com.xwder.app.modules.blog.entity.Tag;
import com.xwder.app.modules.blog.service.intf.ArticleService;
import com.xwder.app.modules.blog.service.intf.CategoryService;
import com.xwder.app.modules.blog.service.intf.TagService;
import com.xwder.app.modules.user.entity.User;
import com.xwder.app.utils.SessionUtil;
import com.xwder.cloud.commmon.api.CommonResult;
import com.xwder.cloud.commmon.api.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author wande
 * @version 1.0
 * @date 2020/07/22
 */
@RequestMapping(value = "/blog/article")
@Controller
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private TagService tagService;


    @RequestMapping("/edit")
    public String articleEdit() {
        return "blog/article/edit";
    }

    @RequestMapping(value = "/preview")
    public String articlePreview(Model model) {
        return "blog/article/proview";
    }

    @RequestMapping(value = "/{articleId}")
    public String article(@PathVariable("articleId") Long articleId, Model model) {
        Article article = articleService.getArticleById(articleId);
        if (article == null) {
            return "blog/article/article";
        }
        Map articleMap = new HashMap();
        Category category = categoryService.getCategoryById(article.getCategoryId());
        articleMap.put("category", category);
        String tags = article.getTags();
        if (StrUtil.isNotEmpty(tags)) {
            String[] splits = tags.split("-");
            List ids = new ArrayList();
            for (String split : splits) {
                ids.add(Long.parseLong(split));
            }
            List tagList = tagService.listTagById(ids);
            articleMap.put("tagList", tagList);
        }
        articleMap.put("article", article);
        model.addAttribute("articleMap",articleMap);
        return "blog/article/article";
    }

    /**
     * 保存、预览、发布
     *
     * @param request
     * @param action
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/save")
    public Object toArticlePreview(HttpServletRequest request, @RequestParam(value = "action") String action) {
        String paramData = request.getParameter("paramData");
        JSONObject jsonObject = JSONUtil.parseObj(paramData);
        String title = (String) jsonObject.get("title");
        String summary = (String) jsonObject.get("summary");
        String content = (String) jsonObject.get("content");
        String type = (String) jsonObject.get("type");
        // 文章id 根据文章id判断时新增还是修改
        String idStr = (String) jsonObject.get("id");
        String categoryId = (String) jsonObject.get("category");
        Category category = categoryService.getCategoryById(Long.parseLong(categoryId));
        JSONArray jsonArray = (JSONArray) jsonObject.get("tags");
        List tags = jsonArray.toList(Integer.class);
        List<Tag> tagList = tagService.listTagById(tags);
        List<Long> tagIds = tagList.stream().map(Tag::getId).collect(Collectors.toList());

        // TODO 参数校验

        User sessionUser = (User) SessionUtil.getSessionAttribute(SysConstant.SESSION_USER);
        // 构造保存或者更新的article实体
        Article article;
        if (StrUtil.isNotBlank(idStr)) {
            article = articleService.getArticleById(Long.valueOf(idStr));
            if (article == null) {
                return CommonResult.failed("文章更新失败，文章不存在");
            }
        } else {
            article = new Article();
            article.setModifieCount(1);
            article.setGmtCreate(new Date());
            article.setReadCount(0L);
            article.setFavors(0L);
            article.setComments(0L);
            article.setStatus(0);
            article.setWeight(0);
            article.setUserId(sessionUser.getId());
            article.setUserName(sessionUser.getUserName());
        }
        article.setCategoryId(category.getId());
        String tagsStr = Joiner.on("-").join(tagIds);
        article.setTags(tagsStr);
        article.setTitle(title);
        article.setSummary(summary);
        article.setContent(content);

        // TODO 封面图片

        article.setModifieCount(article.getModifieCount() + 1);

        Map responseData = new HashMap();
        // 保存只给出响应信息 预览和发布调整到新的页面
        if (StrUtil.equalsAnyIgnoreCase(action, "save")) {
            Article saveArticle = articleService.saveOrUpdateArticle(article);
            responseData.put("id",saveArticle.getId());
            return CommonResult.success(responseData);
        }

        Map articleMap = new HashMap<>();
        articleMap.put("article", article);
        articleMap.put("category", category);
        articleMap.put("tagList", tagList);

        // 预览不保存只查看预览
        if (StrUtil.equalsIgnoreCase(action, "preview")) {
            SessionUtil.setSessionAttribute("previewMap", articleMap);
            String redirectUrl = "/blog/article/preview.html";
            return CommonResult.success(redirectUrl);
        }

        // 发布文章 先保存再调整至发布的文章页面
        if (StrUtil.equalsIgnoreCase(action, "publish")) {
            article.setStatus(1);
            article.setPublishTime(new Date());
            Article saveArticle = articleService.saveOrUpdateArticle(article);
            String redirectUrl = "/blog/article/" + saveArticle.getId() + ".html";
            responseData.put("id",saveArticle.getId());
            responseData.put("redirectUrl",redirectUrl);
            return CommonResult.success(responseData);
        }

        return CommonResult.failed("操作失败");
    }
}

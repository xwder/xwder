package com.xwder.app.modules.blog.controller;

import cn.hutool.core.collection.CollectionUtil;
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
import com.xwder.app.modules.user.service.intf.UserService;
import com.xwder.app.utils.SessionUtil;
import com.xwder.cloud.commmon.api.CommonResult;
import com.xwder.cloud.commmon.constan.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 博客编辑 查看 预览
 *
 * @author wande
 * @version 1.0
 * @date 2020/07/22
 */
@RequestMapping(value = "/blog")
@Controller
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private TagService tagService;

    @Autowired
    private UserService userService;


    /**
     * 从查看博客文章页面点击修改博客
     * 新增、编辑博客文章
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/edit/article")
    public String articleEdit(@RequestParam(value = "id", required = false) Long id, Model model) {
        String templateUrl = "blog/article/edit";
        User sessionUser = (User) SessionUtil.getSessionAttribute(SysConstant.SESSION_USER);
        // 获取用户所有的 category 和 tags
        List<Tag> allTags = tagService.listTagByUserId(sessionUser.getId());
        List<Category> allCategorys = categoryService.listCategoryByUserId(sessionUser.getId());
        model.addAttribute("allTags", allTags);
        model.addAttribute("allCategorys", allCategorys);

        if (id != null) {
            Article article = articleService.getArticleById(id);
            if (article == null) {
                return templateUrl;
            }
            String tagIdsStr = article.getTags();
            String[] splits = tagIdsStr.split("-");
            // 选中的tags id
            List<Long> tagIds = new ArrayList<>();
            for (String split : splits) {
                if (StrUtil.isNotEmpty(split)) {
                    tagIds.add(Long.parseLong(split));
                }
            }
            Category currentCategory = categoryService.getCategoryById(article.getCategoryId());
            model.addAttribute("article", article);
            model.addAttribute("currentCategory", currentCategory);
            model.addAttribute("tagIds", tagIds);
        }
        return templateUrl;
    }

    /**
     * 新增博客文章
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/edit/article/preview")
    public String articlePreview(Model model) {
        return "blog/article/proview";
    }

    /**
     * 保存、预览、发布
     *
     * @param request
     * @param action
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/edit/article")
    public Object editArticle(HttpServletRequest request, @RequestParam(value = "action") String action) {
        String paramData = request.getParameter("paramData");
        JSONObject jsonObject = JSONUtil.parseObj(paramData);
        String title = (String) jsonObject.get("title");
        String summary = (String) jsonObject.get("summary");
        String content = (String) jsonObject.get("content");
        String previewImgUrl = (String) jsonObject.get("previewImgUrl");
        String type = (String) jsonObject.get("type");
        // 博客文章id 根据博客文章id判断时新增还是修改
        String idStr = (String) jsonObject.get("id");
        String categoryId = (String) jsonObject.get("category");
        Category category = categoryService.getCategoryById(Long.parseLong(categoryId));
        JSONArray jsonArray = (JSONArray) jsonObject.get("tags");
        List tags = jsonArray.toList(Integer.class);
        List<Tag> tagList = new ArrayList<>();
        if (tags.size() > 0) {
            tagList = tagService.listTagById(tags);
        }
        List<Long> tagIds = tagList.size() == 0 ? new ArrayList<>() : tagList.stream().map(Tag::getId).collect(Collectors.toList());

        // TODO 参数校验

        User sessionUser = (User) SessionUtil.getSessionAttribute(SysConstant.SESSION_USER);
        // 构造保存或者更新的article实体
        Article article;
        if (StrUtil.isNotBlank(idStr)) {
            article = articleService.getArticleById(Long.valueOf(idStr));
            if (article == null) {
                return CommonResult.failed("博客文章更新失败，博客文章不存在");
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
        article.setPreviewImage(previewImgUrl);
        article.setCategoryId(category.getId());
        String tagsStr = Joiner.on("-").join(tagIds);
        article.setTags(tagsStr);
        article.setTitle(title);
        article.setSummary(summary);
        article.setContent(content);
        article.setArticleType(type);


        Map responseData = new HashMap();
        // 保存只给出响应信息 预览和发布调整到新的页面
        if (StrUtil.equalsAnyIgnoreCase(action, "save")) {
            article.setModifieCount(article.getModifieCount() + 1);
            Article saveArticle = articleService.saveOrUpdateArticle(article);
            responseData.put("id", saveArticle.getId());
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

        // 发布博客文章 先保存再调整至发布的博客文章页面
        if (StrUtil.equalsIgnoreCase(action, "publish")) {
            article.setModifieCount(article.getModifieCount() + 1);
            article.setStatus(1);
            article.setPublishTime(new Date());
            Article saveArticle = articleService.saveOrUpdateArticle(article);
            String redirectUrl = "/blog/article/" + saveArticle.getId() + ".html";
            responseData.put("id", saveArticle.getId());
            responseData.put("redirectUrl", redirectUrl);
            return CommonResult.success(responseData);
        }

        return CommonResult.failed("操作失败");
    }

    /**
     * 查看博客文章
     *
     * @param articleId
     * @param model
     * @return
     */
    @RequestMapping(value = "/article/{articleId}")
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
        model.addAttribute("articleMap", articleMap);
        return "blog/article/article";
    }

    /**
     * 博客文章列表
     *
     * @return
     */
    @RequestMapping(value = {"/article/list", "/article/user/{userName}"})
    public String articleList(@PathVariable(required = false) String userName, HttpServletRequest request,
                              @RequestParam(value = "pageNum", required = false, defaultValue = "1") @Min(1) @Max(10000) Integer pageNum,
                              @RequestParam(value = "pageSize", required = false, defaultValue = "6") @Min(1) @Max(10) Integer pageSize,
                              Model model) {
        String templatesUrl = "/blog/article/list";
        User searchUser = null;
        if (StrUtil.isNotEmpty(userName)) {
            searchUser = userService.getUserByUserName(userName);
        }
        if (searchUser == null) {
            List<User> users = userService.listManagerUser();
            if (CollectionUtil.isNotEmpty(users)) {
                searchUser = users.get(0);
            } else {
                model.addAttribute("articleListError", 0);
                return templatesUrl;
            }
        }

        List<Map> categoryMapList = categoryService.listCategory(searchUser.getId());

        Page<Article> articlePage = articleService.listArticleByUserId(searchUser.getId(), pageNum, pageSize);
        List<Tag> tags = tagService.listTagByUserId(searchUser.getId());
        model.addAttribute("articlePage", articlePage);
        model.addAttribute("articles", articlePage.getContent());
        model.addAttribute("tags", tags);
        model.addAttribute("categoryMapList", categoryMapList);
        return templatesUrl;
    }
}

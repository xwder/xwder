package com.xwder.app.modules.blog.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xwder.app.common.result.CommonResult;
import com.xwder.app.consts.SysConfigConstants;
import com.xwder.app.modules.blog.entity.Article;
import com.xwder.app.modules.blog.entity.ArticleTag;
import com.xwder.app.modules.blog.entity.Category;
import com.xwder.app.modules.blog.entity.Tag;
import com.xwder.app.modules.blog.service.intf.ArticleService;
import com.xwder.app.modules.blog.service.intf.ArticleTagService;
import com.xwder.app.modules.blog.service.intf.CategoryService;
import com.xwder.app.modules.blog.service.intf.TagService;
import com.xwder.app.modules.comment.entity.CommentInfo;
import com.xwder.app.modules.comment.entity.CommentReply;
import com.xwder.app.modules.comment.service.intf.CommentInfoService;
import com.xwder.app.modules.comment.service.intf.CommentReplyService;
import com.xwder.app.modules.user.entity.User;
import com.xwder.app.modules.user.service.intf.UserService;
import com.xwder.app.utils.AssertUtil;
import com.xwder.app.utils.SessionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
@Slf4j
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

    @Autowired
    private ArticleTagService articleTagService;

    @Autowired
    private CommentInfoService commentInfoService;

    @Autowired
    private CommentReplyService commentReplyService;

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
        String templateUrl = "blog/edit";
        User sessionUser = (User) SessionUtil.getSessionAttribute(SysConfigConstants.SESSION_USER);
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
     * 从查看博客文章页面点击修改博客
     * 新增、编辑博客文章
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/editmd/article")
    public String articleEditMd(@RequestParam(value = "id", required = false) Long id, Model model) {
        String templateUrl = "blog/md";
        User sessionUser = (User) SessionUtil.getSessionAttribute(SysConfigConstants.SESSION_USER);
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
    @RequestMapping(value = "/article/preview")
    public String articlePreview(Model model) {
        return "blog/proview";
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
        AssertUtil.paramIsNotNull(title, "标题不能为空");
        String summary = (String) jsonObject.get("summary");
        AssertUtil.paramIsNotNull(summary, "摘要不能为空");
        String content = (String) jsonObject.get("content");
        AssertUtil.paramIsNotNull(content, "内容不能为空");
        String mdContent = (String) jsonObject.get("mdContent");
        String previewImgUrl = (String) jsonObject.get("previewImgUrl");
        String type = (String) jsonObject.get("type");
        AssertUtil.paramIsNotNull(type, "文章类型不能为空");
        // 博客文章id 根据博客文章id判断时新增还是修改
        String idStr = (String) jsonObject.get("id");
        String categoryId = (String) jsonObject.get("category");
        AssertUtil.paramIsNotNull(categoryId, "文章分类不能为空");

        Category category = categoryService.getCategoryById(Long.parseLong(categoryId));
        JSONArray jsonArray = (JSONArray) jsonObject.get("tags");
        List tags = jsonArray.toList(Integer.class);
        List<Tag> tagList = new ArrayList<>();
        if (tags.size() > 0) {
            tagList = tagService.listTagById(tags);
        }
        List<Long> tagIds = tagList.size() == 0 ? new ArrayList<>() : tagList.stream().map(Tag::getId).collect(Collectors.toList());

        User sessionUser = (User) SessionUtil.getSessionAttribute(SysConfigConstants.SESSION_USER);
        // 构造保存或者更新的article实体
        Article article;
        if (StrUtil.isNotBlank(idStr)) {
            article = articleService.getArticleById(Long.valueOf(idStr));
            if (article == null) {
                return CommonResult.failed("博客文章更新失败，博客文章不存在");
            }
        } else {
            article = new Article();
            article.setModifyCount(1);
            Date date = new Date();
            article.setGmtCreate(date);
            article.setGmtModified(date);
            article.setReadCount(0L);
            article.setFavors(0L);
            article.setComments(0L);
            article.setStatus(0);
            article.setWeight(0);
            article.setUserId(sessionUser.getId());
            article.setUserName(sessionUser.getUserName());
            article.setLikes(0L);
            article.setavailable(1);
        }
        article.setPreviewImage(previewImgUrl);
        article.setCategoryId(category.getId());
        String tagsStr = Joiner.on("-").join(tagIds);
        article.setTags(tagsStr);
        article.setTitle(title);
        article.setSummary(summary);
        article.setContent(content);
        article.setArticleType(type);
        article.setMdContent(mdContent);


        Map responseData = new HashMap();
        // 保存只给出响应信息 预览和发布调整到新的页面
        if (StrUtil.equalsAnyIgnoreCase(action, "save")) {
            article.setModifyCount(article.getModifyCount() + 1);
            Article saveArticle = articleService.saveOrUpdateArticle(article);
            // 更新 articleTag 表
            articleTagService.saveOrUpdateArticleTags(sessionUser.getId(), saveArticle.getId(), tagList);
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
            article.setModifyCount(article.getModifyCount() + 1);
            article.setStatus(1);
            article.setPublishTime(new Date());
            Article saveArticle = articleService.saveOrUpdateArticle(article);
            // 更新 articleTag 表
            articleTagService.saveOrUpdateArticleTags(sessionUser.getId(), saveArticle.getId(), tagList);

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
        Article article = null;
        article = articleService.getArticleById(articleId);
        if (article == null) {
            return "blog/article";
        }
        Category category = categoryService.getCategoryById(article.getCategoryId());
        Map articleMap = new HashMap();
        articleMap.put("category", category);

        // 查询该文章的标签
        List<ArticleTag> articleTagList = articleTagService.listArticleTagByArticleId(articleId);
        List<Long> tagIds = articleTagList.stream().map(ArticleTag::getTagId).collect(Collectors.toList());
        if (tagIds != null && !tagIds.isEmpty()) {
            List tagList = tagService.listTagById(tagIds);
            articleMap.put("tagList", tagList);
        }
        articleMap.put("article", article);
        model.addAttribute("articleMap", articleMap);
        // 文章阅读数+1
        Integer readCount = articleService.addArticleReadCount(articleId, Math.toIntExact(article.getReadCount()), 1);
        article.setReadCount(readCount.longValue());

        // 查询文章评论
        List<CommentInfo> commentInfos = commentInfoService.listCommentInfoByTypeAndSubjectId(SysConfigConstants.COMMENT_TYPE_BLOG, articleId);
        if (CollectionUtil.isNotEmpty(commentInfos)) {
            List<Long> commentIds = commentInfos.stream().map(commentInfo -> commentInfo.getId()).collect(Collectors.toList());
            List<CommentReply> commentReplyList = commentReplyService.listCommentReplyByCommentIds(commentIds);
            // 根据评论id 分类回复的评论
            List<Map> commentList = new ArrayList<>();
            for (CommentInfo commentInfo : commentInfos) {
                ArrayList<CommentReply> replyCommentList = Lists.newArrayList();
                for (CommentReply commentReply : commentReplyList) {
                    if (commentInfo.getId().equals(commentReply.getCommentId())) {
                        replyCommentList.add(commentReply);
                    }
                }
                // 一个评论 和 对应评论的回复信息
                HashMap<String, Object> completeCommentMap = Maps.newHashMap();
                completeCommentMap.put("comment", commentInfo);
                // 排序
                Collections.sort(replyCommentList, new Comparator<CommentReply>() {
                    @Override
                    public int compare(CommentReply o1, CommentReply o2) {
                        return o1.getId() > o2.getId() ? -1 : 1;
                    }
                });
                completeCommentMap.put("commentReplys", replyCommentList);
                commentList.add(completeCommentMap);
            }
            // 排序
            Collections.sort(commentList, new Comparator<Map>() {
                @Override
                public int compare(Map o1, Map o2) {
                    CommentInfo commentInfo1 = (CommentInfo) o1.get("comment");
                    CommentInfo commentInfo2 = (CommentInfo) o2.get("comment");
                    return commentInfo1.getId() > commentInfo2.getId() ? -1 : 1;
                }
            });
            model.addAttribute("commentList", commentList);
        }


        User sessionUser = (User) SessionUtil.getSessionAttribute(SysConfigConstants.SESSION_USER);
        model.addAttribute("sessionUser", sessionUser);
        log.info("查看博客文章[{}]内容获取成功",articleId);
        return "blog/article";
    }

    /**
     * TODO @Min(1) @Max(10000) 未生效
     * 博客文章列表
     *
     * @return
     */
    @RequestMapping(value = {"/article/list"})
    public String articleList(HttpServletRequest request,
                              @RequestParam(value = "pageNum", required = false, defaultValue = "1") @Min(1) @Max(10000) Integer pageNum,
                              @RequestParam(value = "pageSize", required = false, defaultValue = "4") @Min(1) @Max(10) Integer pageSize,
                              @RequestParam(value = "categoryId", required = false) Long categoryId,
                              @RequestParam(value = "tagId", required = false) Long tagId,
                              Model model) {
        pageNum = pageNum > 0 ? pageNum : 1;
        pageSize = pageSize > 0 ? pageSize : 4;
        pageSize = pageSize > 1000 ? 1000 : pageSize;
        articleService.listArticleCategoryTag(categoryId, tagId, pageNum, pageSize, model);
        return "blog/list";
    }
}

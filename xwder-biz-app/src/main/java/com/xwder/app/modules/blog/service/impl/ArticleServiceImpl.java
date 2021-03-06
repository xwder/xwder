package com.xwder.app.modules.blog.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Lists;
import com.xwder.app.consts.RedisConstants;
import com.xwder.app.consts.SysConfigConstants;
import com.xwder.app.helper.dao.DAOHelper;
import com.xwder.app.helper.dao.NativeSQL;
import com.xwder.app.modules.blog.dao.BlogDaoResourceHandler;
import com.xwder.app.modules.blog.entity.Article;
import com.xwder.app.modules.blog.entity.ArticleTag;
import com.xwder.app.modules.blog.entity.Category;
import com.xwder.app.modules.blog.entity.Tag;
import com.xwder.app.modules.blog.repository.ArticleRepository;
import com.xwder.app.modules.blog.service.intf.ArticleService;
import com.xwder.app.modules.blog.service.intf.ArticleTagService;
import com.xwder.app.modules.blog.service.intf.CategoryService;
import com.xwder.app.modules.blog.service.intf.TagService;
import com.xwder.app.modules.user.entity.User;
import com.xwder.app.modules.user.service.intf.UserService;
import com.xwder.app.sysmodules.blog.dto.ArticleDto;
import com.xwder.app.utils.PageUtil;
import com.xwder.app.utils.RedisUtil;
import com.xwder.app.utils.SessionUtil;
import com.xwder.app.utils.TimeCountUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 文章service impl
 *
 * @author wande
 * @version 1.0
 * @date 2020/07/22
 */
@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private TagService tagService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ArticleTagService articleTagService;

    /**
     * 根据是否有主键修改或者更新文章
     *
     * @param article 文章
     * @return 保存后的文章
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Article saveOrUpdateArticle(Article article) {
        if (article.getId() != null) {
            // 先删除redis
            String articleRedisKey = RedisConstants.BLOG_ARTICLE_ARTICLE + ":" + article.getId();
            redisUtil.del(articleRedisKey);
            // 获取readCount
            String articleReadCountRedisKey = RedisConstants.BLOG_ARTICLE_READ_COUNT + ":" + article.getId();
            Integer readCount = (Integer) redisUtil.get(articleReadCountRedisKey);
            if (readCount != null) {
                article.setReadCount(readCount.longValue());
            }
        }
        article.setLastModifyTime(new Date());
        return articleRepository.save(article);
    }

    /**
     * 根据id查询文章信息
     *
     * @param articleId 文章id
     * @return 文章
     */
    @Override
    public Article getArticleById(Long articleId) {
        // 先从redis中取
        String articleRedisKey = RedisConstants.BLOG_ARTICLE_ARTICLE + ":" + articleId;
        Article article = (Article) redisUtil.get(articleRedisKey);
        if (article == null) {
            article = articleRepository.findById(articleId).orElse(null);
            if (article != null) {
                // 保存article redis
                redisUtil.set(articleRedisKey, article, -1);
            }
        }
        return article;
    }


    /**
     * 根据用户id、分类 查询文章列表
     *
     * @param userId     用户id
     * @param categoryId 分类id
     * @param tagId      标签id
     * @param pageNum    页码
     * @param pageSize   分页大小
     * @return 分页文章
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Article> listArticleByUserId(Long userId, Long categoryId, Long tagId, Integer pageNum, Integer pageSize) {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize, sort);
        if (categoryId != null) {
            // 根据 用户ID和文章分类 查询文章列表
            return articleRepository.findByUserIdAndCategoryIdAndStatusAndAvailable(userId, categoryId, 1, 1, pageable);
        } else if (tagId != null) {
            // 根据 用户ID和标签 查询文章列表
            Page<ArticleTag> articleTagPage = articleTagService.listArticleTagByUserIdAndArticleId(userId, tagId, pageable);
            List<Long> articleIds = articleTagPage.getContent().stream().map(ArticleTag::getArticleId).collect(Collectors.toList());
            if (articleIds.isEmpty()) {
                return PageUtil.noContentPage(pageable, articleTagPage.getTotalElements());
            }
            List<Article> articleList = articleRepository.listArticleByIds(articleIds);
            return PageUtil.page(articleList, pageable, articleTagPage.getTotalElements());
        }

        return articleRepository.findByUserIdAndStatusAndAvailable(userId, 1, 1, pageable);
    }

    /**
     * 修改article的阅读数
     *
     * @param articleId 文章id
     * @param readCount 增加的阅读数据
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateArticleReadCount(Long articleId, Long readCount) {
        Article article = articleRepository.findById(articleId).get();
        // 如果阅读数没变 return
        if (article != null && article.getReadCount().equals(readCount)) {
            return;
        }
        article.setReadCount(readCount);
        articleRepository.save(article);
        // 更新redis
        String articleRedisKey = RedisConstants.BLOG_ARTICLE_ARTICLE + ":" + articleId;
        redisUtil.set(articleRedisKey, article, -1);
    }

    /**
     * 文章阅读数增加
     *
     * @param articleId      文章id
     * @param existReadCount 数据库中存在的阅读量
     * @param addCount       新增加的阅读数
     * @return 新的阅读数
     */
    @Override
    public Integer addArticleReadCount(Long articleId, Integer existReadCount, Integer addCount) {
        String articleReadCountRedisKey = RedisConstants.BLOG_ARTICLE_READ_COUNT + ":" + articleId;
        Integer readCount = (Integer) redisUtil.get(articleReadCountRedisKey);
        if (readCount != null) {
            readCount += addCount;
        } else {
            readCount = existReadCount;
        }
        redisUtil.set(articleReadCountRedisKey, readCount);
        return readCount;
    }

    /**
     * 修改文章的评论数
     *
     * @param articleId 文章id
     * @param addCount  增加的评论数
     * @return 新的文章评论数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer addArticleCommentCount(Long articleId, Integer addCount) {

        //评论数自增1
        articleRepository.updateArticleCommentCountById(articleId, addCount);

        // 先从redis中取
        String articleRedisKey = RedisConstants.BLOG_ARTICLE_ARTICLE + ":" + articleId;
        Article article = (Article) redisUtil.get(articleRedisKey);
        if (article != null) {
            article.setComments(article.getComments() + addCount);
            redisUtil.set(articleRedisKey, article, -1);
            return article.getComments().intValue();
        }
        return null;
    }

    /**
     * 根据分类id查询文章数量
     *
     * @param categoryId 分类id
     * @return 该分类下的文章数量
     */
    @Override
    public int countByCategoryId(Long categoryId) {
        return articleRepository.countByCategoryId(categoryId);
    }

    /**
     * 分页查询文章
     *
     * @param articleDto 文章dto
     * @return 博客文章分页
     */
    @Override
    @Transactional(readOnly = true)
    public Page listArticlePageData(ArticleDto articleDto) {
        String querySql = DAOHelper.getSQL(BlogDaoResourceHandler.class, "query_article_list");
        Pageable pageable = PageRequest.of(articleDto.getPage(), articleDto.getLimit(), null);
        ArrayList paramList = Lists.newArrayList();
        List<Map> rows = NativeSQL.findByNativeSQLPageable(querySql, paramList, pageable);
        int count = NativeSQL.countByNativeSQL(querySql, paramList);
        return PageUtil.page(rows, pageable, count);
    }

    /**
     * 博客分页分类展示页面
     *
     * @param categoryId 分类id
     * @param tagId      标签id
     * @param pageNum    页码
     * @param pageSize   分页大小
     * @param model      model
     */
    @Override
    public void listArticleCategoryTag(Long categoryId, Long tagId, Integer pageNum, Integer pageSize, Model model) {
        Long startTime = System.currentTimeMillis();
        String templatesUrl = "blog/article/list";
        String currentUrl = "blog/article";
        User searchUser = null;
        // 没有传递用户信息 当前登录用户
        User sessionUser = (User) SessionUtil.getSessionAttribute(SysConfigConstants.SESSION_USER);
        if (searchUser != null) {
            searchUser = sessionUser;
        } else {
            List<User> users = userService.listManagerUser();
            if (CollectionUtil.isNotEmpty(users)) {
                searchUser = users.get(0);
            } else {
                // 没有用户信息
                model.addAttribute("articleListError", 0);
                return;
            }
        }
        assert searchUser != null;
        searchUser.setPassword(null);
        searchUser.setEmail(null);
        searchUser.setSalt(null);


        List<Map> categoryMapList = categoryService.listCategoryArticleCount(searchUser.getId());
        List<Map> tagMaps = tagService.listTagCountByUserId(searchUser.getId());

        // 文章分页内容
        Page<Article> articlePage = null;
        Category category = null;
        Tag tag = null;

        // 默认无分类也无标签
        if (categoryId == null && tagId == null) {
            articlePage = listArticleByUserId(searchUser.getId(), categoryId, tagId, pageNum, pageSize);
        }
        // 有分类无标签
        if (categoryId != null && tagId == null) {
            articlePage = listArticleByUserId(searchUser.getId(), categoryId, tagId, pageNum, pageSize);
            // 分类信息
            category = categoryService.getCategoryById(categoryId);
        }

        // 有标签无分类
        // 无分类有标签
        if (categoryId == null && tagId != null) {
            articlePage = listArticleByUserId(searchUser.getId(), categoryId, tagId, pageNum, pageSize);
            tag = tagService.findTagById(tagId);
        }

        // 显示内容处理
        assert articlePage != null;
        List<Article> articleList = articlePage.getContent();
        for (Article article : articleList) {
            // 格式化显示时间 几小时之前、几天之前
            String remark = TimeCountUtil.format(article.getLastModifyTime());
            article.setRemark(remark);
        }

        model.addAttribute("pageSize", pageSize);
        model.addAttribute("pageNum", pageNum);


        model.addAttribute("categoryId", categoryId);
        model.addAttribute("tagId", tagId);

        model.addAttribute("currentPage", pageNum);
        model.addAttribute("articlePage", articlePage);
        model.addAttribute("articles", articleList);
        model.addAttribute("categoryMapList", categoryMapList);
        model.addAttribute("category", category);

        model.addAttribute("currentUser", searchUser);
        model.addAttribute("currentUrl", currentUrl);

        model.addAttribute("tagMaps", tagMaps);
        model.addAttribute("tag", tag);

        int totalArticles = 0;
        // 计算所有个文章总数
        for (Map map : categoryMapList) {
            int count = Integer.parseInt(map.get("count").toString());
            totalArticles = totalArticles + count;
        }
        model.addAttribute("totalArticles", totalArticles);


        double endTime = System.currentTimeMillis() - startTime;
        double useTime = endTime / 1000;
        model.addAttribute("useTime", useTime);

        // 构建title 栏目标题-第1页-网站标题
        StringBuilder titleSB = new StringBuilder();
        if (categoryId != null && category != null) {
            titleSB.append(category.getCategoryName());
        } else if (tagId != null && tag != null) {
            titleSB.append(tag.getTagName());
        } else {
            titleSB.append("博客文章");
        }
        titleSB.append("-第").append(pageNum).append("页-OneDay");
        model.addAttribute("title", titleSB.toString());
    }

    /**
     * 获取所有博客 id
     *
     * @return 所有博客文章id
     */
    @Override
    public List<BigInteger> listAllBlogArticleId() {
        return articleRepository.listAllBlogArticleId();
    }

    /**
     * 查询所有的文章信息 只包含 id、userid、修改时间
     *
     * @param userId
     * @return
     */
    @Override
    public List<Map> listArticleIdAndArticleUpdateTimeBOrderByUserId(Long userId) {
        return articleRepository.listArticleIdAndArticleUpdateTimeBOrderByUserId(userId);
    }
}

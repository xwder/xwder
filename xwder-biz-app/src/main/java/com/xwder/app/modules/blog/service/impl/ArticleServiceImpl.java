package com.xwder.app.modules.blog.service.impl;

import com.google.common.collect.Lists;
import com.xwder.app.consts.RedisConstant;
import com.xwder.app.helper.dao.DAOHelper;
import com.xwder.app.helper.dao.NativeSQL;
import com.xwder.app.modules.blog.dao.BlogDaoResourceHandler;
import com.xwder.app.modules.blog.entity.Article;
import com.xwder.app.modules.blog.repository.ArticleRepository;
import com.xwder.app.modules.blog.service.intf.ArticleService;
import com.xwder.app.sysmodules.blog.dto.ArticleDto;
import com.xwder.app.utils.PageUtil;
import com.xwder.app.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    /**
     * 根据是否有主键修改或者更新文章
     *
     * @param article
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Article saveOrUpdateArticle(Article article) {
        if (article.getId() != null) {
            // 先删除redis
            String articleRedisKey = RedisConstant.BLOG_ARTICLE_ARTICLE + ":" + article.getId();
            redisUtil.del(articleRedisKey);
            // 获取readCount
            String articleReadCountRedisKey = RedisConstant.BLOG_ARTICLE_READCOUNT + ":" + article.getId();
            Integer readCount = (Integer) redisUtil.get(articleReadCountRedisKey);
            if (readCount != null) {
                article.setReadCount(readCount.longValue());
            }
        }
        Article saveArticle = articleRepository.save(article);
        return saveArticle;
    }

    /**
     * 根据id查询文章信息
     *
     * @param articleId
     * @return
     */
    @Override
    public Article getArticleById(Long articleId) {
        Article article = null;
        // 先从redis中取
        String articleRedisKey = RedisConstant.BLOG_ARTICLE_ARTICLE + ":" + articleId;
        article = (Article) redisUtil.get(articleRedisKey);
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
     * @param userId
     * @param categoryId
     * @param tagId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public Page<Article> listArticleByUserId(Long userId, Long categoryId, Long tagId, Integer pageNum, Integer pageSize) {
        Sort sort = Sort.by(Sort.Direction.DESC, "gmtModified");
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize, sort);
        if (categoryId != null) {
            return articleRepository.findByUserIdAndCategoryIdAndStatusAndAvailable(userId, categoryId, 1, 1, pageable);
        } else if (tagId != null) {

        }
        return articleRepository.findByUserIdAndStatusAndAvailable(userId, 1, 1, pageable);
    }

    /**
     * 修改article的阅读数
     *
     * @param articleId
     * @param readCount
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
        String articleRedisKey = RedisConstant.BLOG_ARTICLE_ARTICLE + ":" + articleId;
        redisUtil.set(articleRedisKey, article, -1);
    }

    /**
     * 文章阅读数增加
     *
     * @param articleId
     * @param existReadCount 数据库中存在的阅读量
     * @param addCount
     */
    @Override
    public Integer addArticleReadCount(Long articleId, Integer existReadCount, Integer addCount) {
        String articleReadCountRedisKey = RedisConstant.BLOG_ARTICLE_READCOUNT + ":" + articleId;
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
     * 根据分类id查询文章数量
     *
     * @param categoryId
     * @returnC
     */
    @Override
    public int countByCategoryId(Long categoryId) {
        return articleRepository.countByCategoryId(categoryId);
    }

    /**
     * 分页查询文章
     *
     * @param articleDto
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public Page listArticlePageData(ArticleDto articleDto) {
        String querySql = DAOHelper.getSQL(BlogDaoResourceHandler.class, "query_article_list");
        Pageable pageable = PageRequest.of(articleDto.getPage(), articleDto.getLimit(), null);
        ArrayList paramList = Lists.newArrayList();
        List<Map> rows = NativeSQL.findByNativeSQLPageable(querySql, paramList, pageable);
        int count = NativeSQL.countByNativeSQL(querySql, paramList);
        Page page = PageUtil.page(rows, pageable, count);
        return page;
    }
}

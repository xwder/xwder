package com.xwder.app.modules.blog.service.impl;

import com.xwder.app.consts.RedisConstant;
import com.xwder.app.modules.blog.entity.Article;
import com.xwder.app.modules.blog.repository.ArticleRepository;
import com.xwder.app.modules.blog.service.intf.ArticleService;
import com.xwder.app.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public Article saveOrUpdateArticle(Article article) {
        if (article.getId()!=null) {
            // 先删除redis
            String articleRedisKey = RedisConstant.BLOG_ARTICLE_ARTICLE + ":" + article.getId();
            redisUtil.del(articleRedisKey);
            // 获取readCount
            String articleReadCountRedisKey = RedisConstant.BLOG_ARTICLE_READCOUNT + ":" + article.getId();
            Integer readCount = (Integer) redisUtil.get(articleReadCountRedisKey);
            article.setReadCount(readCount.longValue());
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
            return articleRepository.findByUserIdAndCategoryIdAndStatusAndAvaliable(userId, categoryId, 1, 1, pageable);
        } else if (tagId != null) {

        }
        return articleRepository.findByUserIdAndStatusAndAvaliable(userId, 1, 1, pageable);
    }

    /**
     * 文章阅读数增加
     *
     * @param articleId
     * @param addCount
     */
    @Override
    public Integer addArticleReadCount(Long articleId, Integer addCount) {
        String articleReadCountRedisKey = RedisConstant.BLOG_ARTICLE_READCOUNT + ":" + articleId;
        Integer readCount = (Integer) redisUtil.get(articleReadCountRedisKey);
        if (readCount != null) {
            readCount += addCount;
        } else {
            readCount = 0;
        }
        redisUtil.set(articleReadCountRedisKey, readCount);
        return readCount;
    }
}

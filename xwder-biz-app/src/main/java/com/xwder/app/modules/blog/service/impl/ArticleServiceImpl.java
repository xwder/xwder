package com.xwder.app.modules.blog.service.impl;

import com.xwder.app.modules.blog.entity.Article;
import com.xwder.app.modules.blog.repository.ArticleRepository;
import com.xwder.app.modules.blog.service.intf.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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


    /**
     * 根据是否有主键修改或者更新文章
     *
     * @param article
     * @return
     */
    @Override
    @Transactional
    public Article saveOrUpdateArticle(Article article) {
        Article saveArticle;
        saveArticle = articleRepository.save(article);
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
        Article article = articleRepository.findById(articleId).orElse(null);
        return article;
    }

    /**
     * TODO
     * 根据用户id查询文章列表
     *
     * @param userId
     * @return
     */
    @Override
    public List<Article> listArticleByUserId(Long userId) {
        return null;
    }
}

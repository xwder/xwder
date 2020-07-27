package com.xwder.app.modules.blog.service.impl;

import com.xwder.app.modules.blog.entity.Article;
import com.xwder.app.modules.blog.repository.ArticleRepository;
import com.xwder.app.modules.blog.service.intf.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
     * 根据用户id、分类 查询文章列表
     *
     * @param userId
     * @return
     */
    @Override
    public Page<Article> listArticleByUserId(Long userId, Long categoryId, Integer pageNum, Integer pageSize) {
        Sort sort = Sort.by(Sort.Direction.DESC, "gmtModified");
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize, sort);
        if (categoryId == null) {
            return articleRepository.findByUserIdAndStatusAndAvaliable(userId, 1, 1, pageable);
        } else {
            return articleRepository.findByUserIdAndCategoryIdAndStatusAndAvaliable(userId, categoryId, 1, 1, pageable);
        }

    }
}

package com.xwder.app.modules.blog.service.intf;

import com.xwder.app.modules.blog.entity.Article;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * 文章service
 *
 * @author wande
 * @version 1.0
 * @date 2020/07/22
 */
public interface ArticleService {

    /**
     * 根据是否有主键修改或者更新文章
     *
     * @param article
     * @return
     */
    Article saveOrUpdateArticle(Article article);

    /**
     * 根据id查询文章信息
     *
     * @param articleId
     * @return
     */
    Article getArticleById(Long articleId);


    /**
     * 根据用户id查询文章列表
     *
     * @param userId
     * @param pageNum
     * @param pageSize
     * @return
     */
    Page<Article> listArticleByUserId(Long userId, Integer pageNum, Integer pageSize);


}

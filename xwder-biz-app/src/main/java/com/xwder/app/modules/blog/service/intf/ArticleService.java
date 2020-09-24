package com.xwder.app.modules.blog.service.intf;

import com.xwder.app.modules.blog.entity.Article;
import com.xwder.app.sysmodules.blog.dto.ArticleDto;
import com.xwder.app.sysmodules.blog.dto.CategoryDto;
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
     * 文章阅读数增加
     *
     * @param articleId
     * @param existReadCount 数据库中存在的阅读量
     * @param addCount
     */
    Integer addArticleReadCount(Long articleId, Integer existReadCount, Integer addCount);


    /**
     * 根据用户id查询文章列表
     *
     * @param userId
     * @param categoryId
     * @param tagId
     * @param pageNum
     * @param pageSize
     * @return
     */
    Page<Article> listArticleByUserId(Long userId, Long categoryId, Long tagId, Integer pageNum, Integer pageSize);

    /**
     * 修改article的阅读数
     *
     * @param articleId
     * @param readCount
     */
    void updateArticleReadCount(Long articleId, Long readCount);

    /**
     * 根据分类id查询文章数量
     * @param categoryId
     * @returnC
     */
    int countByCategoryId(Long categoryId);

    /**
     * 分页查询文章
     *
     * @param articleDto
     * @return
     */
    Page listArticlePageData(ArticleDto articleDto);
}

package com.xwder.app.modules.blog.repository;

import com.xwder.app.common.jpa.repository.BaseJpaRepository;
import com.xwder.app.modules.blog.entity.ArticleTag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleTagRepository extends BaseJpaRepository<ArticleTag, Long> {

    /**
     * 根据userid和articleId查询 articleTag
     *
     * @param userId
     * @param articleId
     * @return
     */
    List<ArticleTag> findByUserIdAndArticleId(Long userId, Long articleId);

    /**
     * 根据用户id和tagId查询 articleTag list
     *
     * @param userId
     * @param tagId
     * @param pageable
     * @return
     */
    Page<ArticleTag> findByUserIdAndTagId(Long userId, Long tagId, Pageable pageable);

    /**
     * 批量删除articleTag
     *
     * @param articleTags
     */
    @Override
    void deleteInBatch(Iterable<ArticleTag> articleTags);

    /**
     * 根据文章id查询articleTag
     *
     * @param articleId
     * @return
     */
    List<ArticleTag> findAllByArticleId(Long articleId);
}

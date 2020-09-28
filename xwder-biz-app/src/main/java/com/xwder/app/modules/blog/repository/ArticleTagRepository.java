package com.xwder.app.modules.blog.repository;

import com.xwder.app.common.jpa.repository.BaseJpaRepository;
import com.xwder.app.modules.blog.entity.ArticleTag;
import org.springframework.data.jpa.repository.Query;
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
     * 批量删除articleTag
     *
     * @param articleTags
     */
    @Override
    void deleteInBatch(Iterable<ArticleTag> articleTags);
}

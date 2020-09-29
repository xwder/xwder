package com.xwder.app.modules.blog.service.intf;

import com.xwder.app.modules.blog.entity.ArticleTag;
import com.xwder.app.modules.blog.entity.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * article_tag 接口
 *
 * @author xwder
 * @date 2020年9月28日
 */
public interface ArticleTagService {

    /**
     * 更新博客文章tag 先查询-删除-再保存
     *
     * @param userId
     * @param articleId
     * @param tags
     * @return
     */
    List<ArticleTag> saveOrUpdateArticleTags(Long userId, Long articleId, List<Tag> tags);

    /**
     * 根据文章id查询 articleTag
     *
     * @param articleId
     * @return
     */
    List<ArticleTag> listArticleTagByArticleId(Long articleId);

    /**
     * 根据用户id和tagId查询 articleTag list
     *
     * @param userId
     * @param tagId
     * @param pageable
     * @return
     */
    Page<ArticleTag> listArticleTagByUserIdAndArticleId(Long userId, Long tagId, Pageable pageable);

}

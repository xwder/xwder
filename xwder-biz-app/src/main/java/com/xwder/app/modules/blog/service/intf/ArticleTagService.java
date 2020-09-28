package com.xwder.app.modules.blog.service.intf;

import com.xwder.app.modules.blog.entity.ArticleTag;
import com.xwder.app.modules.blog.entity.Tag;

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

}

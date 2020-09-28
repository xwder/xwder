package com.xwder.app.modules.blog.service.impl;

import com.xwder.app.modules.blog.entity.ArticleTag;
import com.xwder.app.modules.blog.entity.Tag;
import com.xwder.app.modules.blog.repository.ArticleTagRepository;
import com.xwder.app.modules.blog.service.intf.ArticleTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * article_tag 接口实现类
 *
 * @author xwder
 * @date 2020年9月28日
 */
@Service
public class ArticleTagServiceImpl implements ArticleTagService {

    @Autowired
    private ArticleTagRepository articleTagRepository;

    /**
     * 更新博客文章tag 先查询-删除-再保存
     *
     * @param userId
     * @param articleId
     * @param tags
     * @return
     */
    @Override
    @Transactional(rollbackFor = {Exception.class})
    public List<ArticleTag> saveOrUpdateArticleTags(Long userId, Long articleId, List<Tag> tags) {
        // 现根据userId articleId 查询所有的 articleTag
        List<ArticleTag> existArticleTags = articleTagRepository.findByUserIdAndArticleId(userId, articleId);
        if (existArticleTags != null && !existArticleTags.isEmpty()) {
            articleTagRepository.deleteInBatch(existArticleTags);
        }
        List<ArticleTag> articleTagList = new ArrayList<>();
        // 保存新的 articleTag
        for (Tag tag : tags) {
            ArticleTag articleTag = new ArticleTag();
            articleTag.setUserId(userId);
            articleTag.setArticleId(articleId);
            articleTag.setTagId(tag.getId());
            ArticleTag saveArticle = articleTagRepository.save(articleTag);
            articleTagList.add(saveArticle);
        }
        return articleTagList;
    }
}

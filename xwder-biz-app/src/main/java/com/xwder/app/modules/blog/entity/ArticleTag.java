package com.xwder.app.modules.blog.entity;

import javax.persistence.*;

/**
 * @team xwder
 * @Author xwder
 * @Date 2020-09-28 16:40:53
 */
@Entity
@Table(name = "blog_article_tag")
public class ArticleTag {

    private static final long serialVersionUID = 2687889166417861973L;

    /**
     * 主键
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 用户Id
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 文章Id
     */
    @Column(name = "article_id")
    private Long articleId;

    /**
     * 标签Id
     */
    @Column(name = "tag_id")
    private Long tagId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }
}

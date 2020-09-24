package com.xwder.app.modules.blog.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @team xwder
 * @Author xwder
 * @Date 2020-07-22 16:40:53
 */
@Entity
@Table(name = "blog_article")
public class Article implements Serializable {

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
     * 用户Id
     */
    @Column(name = "user_name")
    private String userName;

    /**
     * 分类ID
     */
    @Column(name = "category_id")
    private Long categoryId;

    /**
     * 标签ID组合 1-2-3
     */
    @Column(name = "tags")
    private String tags;

    /**
     * 文章标题
     */
    @Column(name = "title")
    private String title;

    /**
     * 文章内容
     */
    @Column(name = "content")
    private String content;

    /**
     * 摘要
     */
    @Column(name = "summary")
    private String summary;

    /**
     * 预览图片
     */
    @Column(name = "preview_image")
    private String previewImage;

    /**
     * 收藏数
     */
    @Column(name = "favors")
    private Long favors;

    /**
     * 点赞数量
     */
    @Column(name = "likes")
    private Long likes;

    /**
     * 评论数
     */
    @Column(name = "comments")
    private Long comments;

    /**
     * 阅读数
     */
    @Column(name = "read_count")
    private Long readCount;

    /**
     * 文章状态 0-未发布 1-已发布
     */
    @Column(name = "status")
    private Integer status;

    /**
     * 文章类型 原创 转载 翻译
     */
    @Column(name = "article_type")
    private String articleType = "原创";

    /**
     * 文章编辑次数
     */
    @Column(name = "modifie_count")
    private Integer modifieCount;

    /**
     * 发布时间
     */
    @Column(name = "publish_time")
    private Date publishTime;

    /**
     * 权重
     */
    @Column(name = "weight")
    private Integer weight;

    /**
     * 是否可用
     */
    @Column(name = "is_available")
    private Integer available;

    /**
     * 备注
     */
    @Column(name = "remark")
    private String remark;

    /**
     * 创建时间
     */
    @Column(name = "gmt_create")
    private Date gmtCreate;

    /**
     * 修改时间
     */
    @Column(name = "gmt_modified")
    private Date gmtModified;

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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }


    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }


    public String getPreviewImage() {
        return previewImage;
    }

    public void setPreviewImage(String previewImage) {
        this.previewImage = previewImage;
    }


    public Long getFavors() {
        return favors;
    }

    public void setFavors(Long favors) {
        this.favors = favors;
    }


    public Long getComments() {
        return comments;
    }

    public void setComments(Long comments) {
        this.comments = comments;
    }


    public Long getReadCount() {
        return readCount;
    }

    public void setReadCount(Long readCount) {
        this.readCount = readCount;
    }


    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }


    public Integer getModifieCount() {
        return modifieCount;
    }

    public void setModifieCount(Integer modifieCount) {
        this.modifieCount = modifieCount;
    }


    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }


    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }


    public Integer getAvailable() {
        return available;
    }

    public void setavailable(Integer isAvailable) {
        this.available = isAvailable;
    }


    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }


    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }


    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public String getArticleType() {
        return articleType;
    }

    public void setArticleType(String articleType) {
        this.articleType = articleType;
    }

    public Long getLikes() {
        return likes;
    }

    public void setLikes(Long likes) {
        this.likes = likes;
    }
}

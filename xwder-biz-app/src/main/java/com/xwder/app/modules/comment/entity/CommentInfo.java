package com.xwder.app.modules.comment.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * 评论表回复表
 *
 * @Author xwder
 * @Date 2020-10-23 09:50:09
 */
@Entity
@Table(name = "comments_info")
public class CommentInfo implements Serializable {

    private static final long serialVersionUID = 1779199745295813796L;

    /**
     * 主键
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 评论类型：对人评论，对博客评论，对资源评论，对视频评论
     */
    @Column(name = "type")
    private Integer type;

    /**
     * 被评论内容的主键id 比如博客文章id
     */
    @NotNull(message = "评论对象编号不能为空")
    @Column(name = "subject_id")
    private Long subjectId;

    /**
     * 评论访问地址
     */
    @NotBlank(message = "评论地址不能为空")
    @NotNull(message = "评论地址不能为空")
    @Column(name = "comment_url")
    private String commentUrl;

    /**
     * 被评论者id，可以是人、项目、资源
     */
    @NotNull(message = "被评论对象编号不能为空")
    @Column(name = "owner_id")
    private String ownerId;

    /**
     * 评论者id
     */
    @Column(name = "from_id")
    private Long fromId;

    /**
     * 评论者名称
     */
    @Transient
    private String fromName;

    /**
     * 评论者头像地址
     */
    @Transient
    private String fromAvatar;

    /**
     * 点赞的数量
     */
    @Column(name = "like_num")
    private Long likeNum;

    /**
     * 评论内容
     */
    @NotBlank(message = "评论内容不能为空")
    @NotNull(message = "评论内容不能为空")
    @Column(name = "content")
    private String content;

    /**
     * 评论时间
     */
    @Column(name = "comment_time")
    private Date commentTime;

    /**
     * 0-删除不可用，1-未删除可用
     */
    @Column(name = "available")
    private Integer available;

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


    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }


    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }


    public String getCommentUrl() {
        return commentUrl;
    }

    public void setCommentUrl(String commentUrl) {
        this.commentUrl = commentUrl;
    }


    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }


    public Long getFromId() {
        return fromId;
    }

    public void setFromId(Long fromId) {
        this.fromId = fromId;
    }

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public String getFromAvatar() {
        return fromAvatar;
    }

    public void setFromAvatar(String fromAvatar) {
        this.fromAvatar = fromAvatar;
    }

    public Long getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(Long likeNum) {
        this.likeNum = likeNum;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public Date getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(Date commentTime) {
        this.commentTime = commentTime;
    }


    public Integer getAvailable() {
        return available;
    }

    public void setAvailable(Integer available) {
        this.available = available;
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

}

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
@Table(name = "comments_reply")
public class CommentReply implements Serializable {

    private static final long serialVersionUID = 8048011295873707680L;

    /**
     * 主键
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 评论主表id
     */
    @NotNull(message = "被回复评论id不能为空")
    @Column(name = "comment_id")
    private Long commentId;

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
     * 被评论者id
     */
    @NotNull(message = "被回复者的id不能为空")
    @Column(name = "to_id")
    private Long toId;

    /**
     * 被评论者名称
     */
    @Transient
    private String toName;

    /**
     * 被评论者头像地址
     */
    @Transient
    private String toAvatar;

    /**
     * 评论时间
     */
    @Column(name = "comment_time")
    private Date commentTime;

    /**
     * 点赞的数量
     */
    @Column(name = "like_num")
    private Long likeNum;

    /**
     * 评论内容
     */
    @NotBlank(message = "回复评论内容不能为空")
    @NotNull(message = "回复评论内容不能为空")
    @Column(name = "content")
    private String content;

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


    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
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


    public Long getToId() {
        return toId;
    }

    public void setToId(Long toId) {
        this.toId = toId;
    }

    public String getToName() {
        return toName;
    }

    public void setToName(String toName) {
        this.toName = toName;
    }

    public String getToAvatar() {
        return toAvatar;
    }

    public void setToAvatar(String toAvatar) {
        this.toAvatar = toAvatar;
    }

    public Date getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(Date commentTime) {
        this.commentTime = commentTime;
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

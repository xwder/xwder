package com.xwder.app.modules.blog.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @team xwder
 * @Author xwder
 * @Date 2020-07-22 15:28:15
 */
@Entity
@Table(name = "blog_tag")
public class Tag implements Serializable {

    private static final long serialVersionUID = 9002213713011368216L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 用户id
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 标签名称
     */
    @Column(name = "tag_name")
    private String tagName;

    /**
     * 是否可用
     */
    @Column(name = "is_avaliable")
    private Integer available;

    @Column(name = "gmt_create")
    private Date gmtCreate;

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


    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }


    public Integer getAvailable() {
        return available;
    }

    public void setAvailable(Integer isAvaliable) {
        this.available = isAvaliable;
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

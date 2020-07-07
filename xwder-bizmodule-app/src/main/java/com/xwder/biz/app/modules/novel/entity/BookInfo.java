package com.xwder.biz.app.modules.novel.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author wande
 * @version 1.0
 * @date 2020/07/07
 */
@Data
@Entity
@Table(name = "book_info")
public class BookInfo {

    @Id
    @Column(name = "id")
    private Integer id;

    /**
     *
     */
    @Column(name = "book_name")
    private String bookName;

    /**
     *
     */
    @Column(name = "author")
    private String author;

    /**
     *
     */
    @Column(name = "category")
    private String category;

    /**
     *
     */
    @Column(name = "update_status")
    private String updateStatus;

    /**
     *
     */
    @Column(name = "word_size")
    private Integer wordSize;

    /**
     *
     */
    @Column(name = "last_update_time")
    private Date lastUpdateTime;

    /**
     *
     */
    @Column(name = "latest_chapter")
    private String latestChapter;

    /**
     *
     */
    @Column(name = "book_desc")
    private String bookDesc;
    /**
     *
     */
    @Column(name = "book_img_source")
    private String bookImgSource;

    /**
     *
     */
    @Column(name = "book_img")
    private String bookImg;

    /**
     *
     */
    @Column(name = "book_url")
    private String bookUrl;

    /**
     *
     */
    @Column(name = "gmt_create")
    private Date gmtCreate;

    /**
     *
     */
    @Column(name = "gmt_modified")
    private Date gmtModified;

}

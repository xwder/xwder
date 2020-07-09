package com.xwder.app.modules.novel.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @author wande
 * @version 1.0
 * @date 2020/07/07
 */
@Data
@Entity
@Table(name = "book_chapter")
public class BookChapter {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
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
    @Column(name = "book_id")
    private Integer bookId;

    /**
     *
     */
    @Column(name = "author")
    private String author;

    /**
     *
     */
    @Column(name = "chapter_word_size")
    private Integer chapterWordSize;

    /**
     *
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     *
     */
    @Column(name = "source_url")
    private String sourceUrl;

    /**
     *
     */
    @Column(name = "chapter_no")
    private Integer chapterNo;

    /**
     *
     */
    @Column(name = "chapter_sequence")
    private String chapterSequence;
    /**
     *
     */
    @Column(name = "chapter_article")
    private String chapterArticle;

    /**
     *
     */
    @Column(name = "chapter_name")
    private String chapterName;

    /**
     *
     */
    @Column(name = "chapter_content")
    private String chapterContent;

    /**
     *
     */
    @Column(name = "chapter_content_url")
    private String chapterContentUrl;

    /**
     *
     */
    @Column(name = "volume_name")
    private String volumeName;

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

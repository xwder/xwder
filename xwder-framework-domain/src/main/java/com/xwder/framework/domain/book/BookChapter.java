package com.xwder.framework.domain.book;

import java.util.Date;

public class BookChapter {
    private Integer id;

    private String bookName;

    private Integer bookId;

    private String author;

    private Integer chapterWordSize;

    private Date updateTime;

    private String sourceUrl;

    private Integer chapterNo;

    private String chapterSequence;

    private String chapterArticle;

    private String chapterName;

    private String volumeName;

    private Date gmtCreate;

    private Date gmtModified;

    private String chapterContent;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName == null ? null : bookName.trim();
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author == null ? null : author.trim();
    }

    public Integer getChapterWordSize() {
        return chapterWordSize;
    }

    public void setChapterWordSize(Integer chapterWordSize) {
        this.chapterWordSize = chapterWordSize;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl == null ? null : sourceUrl.trim();
    }

    public Integer getChapterNo() {
        return chapterNo;
    }

    public void setChapterNo(Integer chapterNo) {
        this.chapterNo = chapterNo;
    }

    public String getChapterSequence() {
        return chapterSequence;
    }

    public void setChapterSequence(String chapterSequence) {
        this.chapterSequence = chapterSequence == null ? null : chapterSequence.trim();
    }

    public String getChapterArticle() {
        return chapterArticle;
    }

    public void setChapterArticle(String chapterArticle) {
        this.chapterArticle = chapterArticle == null ? null : chapterArticle.trim();
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName == null ? null : chapterName.trim();
    }

    public String getVolumeName() {
        return volumeName;
    }

    public void setVolumeName(String volumeName) {
        this.volumeName = volumeName == null ? null : volumeName.trim();
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

    public String getChapterContent() {
        return chapterContent;
    }

    public void setChapterContent(String chapterContent) {
        this.chapterContent = chapterContent == null ? null : chapterContent.trim();
    }
}
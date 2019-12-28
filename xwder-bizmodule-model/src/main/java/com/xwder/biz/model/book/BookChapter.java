package com.xwder.biz.model.book;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 小说章节信息
 *
 * @author xwder
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

}
package com.xwder.biz.book.dao;

import com.xwder.biz.model.book.BookChapter;
import com.xwder.biz.myMapper.MyMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @Author: xwder
 * @Date: 2019/7/15 23:51
 * @Description:
 */
public interface BookChapterCustomerMapper extends MyMapper<BookChapter> {

    /**
     * 分页查询章节信息根据bookId,不包含章节内容
     * @param bookChapter
     * @return
     */
    @Select("select id,book_name,book_id,author,chapter_word_size,update_time," +
            "source_url,chapter_no,chapter_sequence,chapter_article,chapter_name,volume_name,gmt_create,gmt_modified from book_chapter where book_id = #{bookId} ")
    List<BookChapter> listBookChapterByBookIdNoContent(BookChapter bookChapter);


    /**
     * 分页查询章节信息根据bookId,全部字段信息
     * @param bookChapter
     * @return
     */
    @Select("select * from book_chapter where book_id = #{bookId}")
    List<BookChapter> listBookChapterByBookId(BookChapter bookChapter);


    /**
     * 查询章节信息
     * @param map
     * @return
     */
    List<BookChapter> listBookChapter(Map map);
}
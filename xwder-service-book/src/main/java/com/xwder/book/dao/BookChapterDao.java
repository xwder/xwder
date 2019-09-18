package com.xwder.book.dao;

import com.xwder.framework.domain.book.BookChapter;
import com.xwder.myMapper.MyMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author: xwder
 * @Date: 2019/7/15 23:51
 * @Description:
 */
public interface BookChapterDao extends MyMapper<BookChapter> {


    @Select("<script>" + "select id,book_name,book_id,author,chapter_word_size,update_time," +
            "source_url,chapter_no,chapter_sequence,chapter_article,chapter_name,volume_name,gmt_create,gmt_modified from book_chapter where book_id = #{bookId} </script>")
    List<BookChapter> listBookChapterByBookId(BookChapter bookChapter);

}

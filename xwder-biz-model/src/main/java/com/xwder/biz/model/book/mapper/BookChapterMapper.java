package com.xwder.biz.model.book.mapper;


import com.xwder.biz.model.book.BookChapter;
import com.xwder.biz.model.book.BookChapterExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BookChapterMapper {
    int countByExample(BookChapterExample example);

    int deleteByExample(BookChapterExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BookChapter record);

    int insertSelective(BookChapter record);

    List<BookChapter> selectByExampleWithBLOBs(BookChapterExample example);

    List<BookChapter> selectByExample(BookChapterExample example);

    BookChapter selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BookChapter record, @Param("example") BookChapterExample example);

    int updateByExampleWithBLOBs(@Param("record") BookChapter record, @Param("example") BookChapterExample example);

    int updateByExample(@Param("record") BookChapter record, @Param("example") BookChapterExample example);

    int updateByPrimaryKeySelective(BookChapter record);

    int updateByPrimaryKeyWithBLOBs(BookChapter record);

    int updateByPrimaryKey(BookChapter record);
}
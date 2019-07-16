package com.xwder.framework.domain.book.mapper;

import com.xwder.framework.domain.book.BookChapter;
import com.xwder.framework.domain.book.BookChapterExample;
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
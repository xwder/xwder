package com.xwder.framework.domain.book.mapper;

import com.xwder.framework.domain.book.BookInfo;
import com.xwder.framework.domain.book.BookInfoExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BookInfoMapper {
    int countByExample(BookInfoExample example);

    int deleteByExample(BookInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BookInfo record);

    int insertSelective(BookInfo record);

    List<BookInfo> selectByExample(BookInfoExample example);

    BookInfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BookInfo record, @Param("example") BookInfoExample example);

    int updateByExample(@Param("record") BookInfo record, @Param("example") BookInfoExample example);

    int updateByPrimaryKeySelective(BookInfo record);

    int updateByPrimaryKey(BookInfo record);
}
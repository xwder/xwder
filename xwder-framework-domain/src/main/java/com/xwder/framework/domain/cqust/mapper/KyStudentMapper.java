package com.xwder.framework.domain.cqust.mapper;

import com.xwder.framework.domain.cqust.KyStudent;
import com.xwder.framework.domain.cqust.KyStudentExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface KyStudentMapper {
    int countByExample(KyStudentExample example);

    int deleteByExample(KyStudentExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(KyStudent record);

    int insertSelective(KyStudent record);

    List<KyStudent> selectByExample(KyStudentExample example);

    KyStudent selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") KyStudent record, @Param("example") KyStudentExample example);

    int updateByExample(@Param("record") KyStudent record, @Param("example") KyStudentExample example);

    int updateByPrimaryKeySelective(KyStudent record);

    int updateByPrimaryKey(KyStudent record);
}
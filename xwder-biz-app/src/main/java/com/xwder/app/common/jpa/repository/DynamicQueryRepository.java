package com.xwder.app.common.jpa.repository;

import java.util.List;
import java.util.Map;

import com.xwder.app.common.jpa.domain.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

@NoRepositoryBean
public interface DynamicQueryRepository<T, ID> extends Repository<T, ID> {
    List<T> findAll(DataQuery dataQuery);

    List<T> findAll(DataQuery dataQuery, Sort sort);

    Page<T> findAll(DataQuery dataQuery, Pageable pageable);

    List<T> findAll(SortableDataQuery sortableDataQuery);

    Page<T> findAll(PageableDataQuery pageableDataQuery);

    Page<T> findAll(PageAndSortDataQuery pageAndSortDataQuery);

    Page<T> findAll(CommonPageQuery commonPageQuery);

    List<T> findAllWidthOutPage(CommonPageQuery commonPageQuery);
/*
    List<T> findByRsql(String sql);

    Page<T> findByRsql(String sql, Pageable pageable);

    List<T> findByRsql(String sql, Sort sort);

    long findByRsqlCount(String sql);

    List<Map> findByNativeSQL(String sql, List paramList);

    List<Map> findByNativeSQL(String sql, List paramList, Pageable pageable);

    <Z> List<Z> findByNativeSQL(Class<Z> zClass, String sql, List paramList);

    <Z> List<Z> findByNativeSQL(Class<Z> zClass, String sql, List paramList, Pageable var4);

    void execute(String sql);

    int update(String sql, Object... obj);

    void deleteLogic(T t);

    void deleteLogic(List<T> list);

    void deleteLogic(String sql);

    void deleteLogicIDList(List<String> list);*/
}
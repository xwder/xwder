package com.xwder.app.helper.dao;

import cn.hutool.core.collection.CollectionUtil;
import com.xwder.app.utils.ListUtil;
import org.hibernate.query.internal.NativeQueryImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 使用时 需要添加事务 @Transactional 注解，不然会 Query.unwrap 报错com.sun.proxy.$Proxy cannot be cast to org.hibernate.query
 * @author wande
 * @version 1.0
 * @date 2020/08/05
 */
@Repository
public class NativeSQL {
    private static EntityManager em;
    private static JdbcTemplate jdbcTemplate;

    public NativeSQL(EntityManager em, JdbcTemplate jdbcTemplate) {
        NativeSQL.em = em;
        NativeSQL.jdbcTemplate = jdbcTemplate;
    }

    /**
     * <p>通过sql原生语句条件查询表列表数据.
     * </p>
     * <pre>
     * List params = new ArrayList();
     * params.add(10);
     * String strSql = "select * from table where intField > ? order by intField"
     * findByNativeSQL(strSql,conditionStr,params);
     * findByNativeSQL(class,conditionStr,params,0,10);
     * </pre>
     *
     * @param strSql
     * @param params
     * @param splitPage
     * @return
     */
    public static List<Map> findByNativeSQL(String strSql, List params, Integer... splitPage) {
        Query query = em.createNativeQuery(strSql);
        // 修改为MapResultTransformer @roger
        query.unwrap(NativeQueryImpl.class).setResultTransformer(MapResultTransformer.INSTANSE);

        if (CollectionUtil.isNotEmpty(params)) {
            for (int i = 0; i < params.size(); i++) {
                query.setParameter(i + 1, params.get(i));
            }
        }

        SQLCommon.setPageAdapter(query, splitPage);
        return query.getResultList();
    }

    /**
     * 通过原生Sql进行查询，返回dto对象，
     *
     * @param strSql   原生Sql，如：select emp_name,age from emp .查询结果会自动转换为驼峰命名规则
     * @param params   查询参数
     * @param dtoClass 需转换的java pojo
     * @param <T>      pojo类型
     * @return 返回List集合
     */
    public static <T> List<T> findByNativeSQLPageable(String strSql, List params, Class<T> dtoClass, Pageable pageable) {
        Query query = JpaContext.getEntityManager().createNativeQuery(strSql);
        query.unwrap(NativeQueryImpl.class).setResultTransformer(new DtoResultTransformer(dtoClass));
        if (CollectionUtil.isNotEmpty(params)) {
            for (int i = 0; i < params.size(); ++i) {
                query.setParameter(i + 1, params.get(i));
            }
        }

        if (pageable != null) {
            query.setFirstResult(SQLCommon.calcStartFromPageable(pageable));
            query.setMaxResults(SQLCommon.calcLimitFromPageable(pageable));
        }

        return query.getResultList();
    }

    /**
     * 通过原生Sql进行查询，返回List<Map>对象，
     *
     * @param strSql 原生Sql，如：select emp_name,age from emp .查询结果会自动转换为驼峰命名规则
     * @param params 查询参数
     * @return 返回List集合
     */
    public static List<Map> findByNativeSQLPageable(String strSql, List params, Pageable pageable) {
        Query query = em.createNativeQuery(strSql);
        query.unwrap(NativeQueryImpl.class).setResultTransformer(MapResultTransformer.INSTANSE);
        if (CollectionUtil.isNotEmpty(params)) {
            for (int i = 0; i < params.size(); ++i) {
                query.setParameter(i + 1, params.get(i));
            }
        }

        if (pageable != null) {
            query.setFirstResult(SQLCommon.calcStartFromPageable(pageable));
            query.setMaxResults(SQLCommon.calcLimitFromPageable(pageable));
        }

        return query.getResultList();
    }

    /**
     * <p>通过sql原生语句条件查询表单行数据.
     * </p>
     * <pre>
     * List params = new ArrayList();
     * params.add(10);
     * String strSql = "select * from table where intField = ? order by intField"
     * findOneByNativeSQL(strSql,params);
     * </pre>
     *
     * @param strSql
     * @param params
     * @return
     */
    public static Map findOneByNativeSQL(String strSql, List params) {
        return ListUtil.get(findByNativeSQL(strSql, params), 0);
    }

    /**
     * <p>通过sql原生语句条件查询数据行数.
     * </p>
     * <pre>
     * List params = new ArrayList();
     * params.add(10);
     * String strSql = "select * from table where intField > ? order by intField"
     * countByNativeSQL(strSql,conditionStr,params);
     * countByNativeSQL(class,conditionStr,params,0,10);
     * </pre>
     *
     * @param strSql
     * @param params
     * @return
     */
    public static int countByNativeSQL(final String strSql, final List params) {
        String countStrSql = "select count(*) as count from ( " + strSql + " ) total";

        Map mapResult = findOneByNativeSQL(countStrSql, params);

        return ((BigInteger) mapResult.get("count")).intValue();
    }

    /**
     * <p>执行sql原生修改语句更新数据，返回更新成功行数.
     * </p>
     * <pre>
     * List lstParams = new ArrayList();
     * lstParams.add(10);
     * lstParams.add("ABC");
     * String strSql = "update sone_demo_crud_test set intField=? where strField = ?";
     * updateByNativeSQL(,lstParams);
     * </pre>
     *
     * @param strSql
     * @param params
     * @return
     */
    public static int updateByNativeSQL(String strSql, List params) {
        Object[] objParams = null;

        if (ListUtil.isNotEmpty(params)) {
            objParams = params.toArray();
        }

        return jdbcTemplate.update(strSql, objParams);
    }

    /**
     * <p>执行sql原生批量修改语句更新数据，返回批量更新成功行数数组.
     * </p>
     * <pre>
     * List lstParams1 = new ArrayList();
     * lstParams1.add(10);
     * lstParams1.add("ABC");
     *
     * List lstParams2 = new ArrayList();
     * lstParams2.add(20);
     * lstParams2.add("ABC");
     *
     * List lstParams = new ArrayList();
     * lstParams.add(lstParams1);
     * lstParams.add(lstParams2);
     * String strSql = "update sone_demo_crud_test set intField=? where strField = ?";
     * updateByNativeSQL(strSql,lstParams);
     * </pre>
     *
     * @param strSql
     * @param params
     * @return
     */
    public static int[] batchUpdateByNativeSQL(String strSql, List<List> params) {
        List batchLstParams = new ArrayList();

        if (ListUtil.isNotEmpty(params)) {
            for (int i = 0; i < params.size(); i++) {
                List lstParams = ListUtil.get(params, i);
                Object[] objParams = null;

                if (ListUtil.isNotEmpty(lstParams)) {
                    objParams = lstParams.toArray();
                }
                batchLstParams.add(objParams);
            }
        }
        return jdbcTemplate.batchUpdate(strSql, batchLstParams);
    }

    /**
     * 执行native sql
     *
     * @param sql
     */
    public static void execute(String sql) {
        jdbcTemplate.execute(sql);
    }

    /**
     * 更新语句
     *
     * @param sql
     * @param params
     */
    public static int update(String sql, Object... params) {
        return jdbcTemplate.update(sql, params);
    }

    /**
     * 查询List
     *
     * @param sql
     * @param elementType
     * @param params
     * @param <T>
     * @return
     */
    public static <T> List<T> queryForList(String sql, Class<T> elementType, Object... params) {
        return jdbcTemplate.queryForList(sql, elementType, params);
    }

}

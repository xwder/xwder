package com.xwder.app.helper.dao;

/**
 * @author wande
 * @version 1.0
 * @date 2020/08/05
 */
import com.xwder.app.attribute.SysConfigAttribute;
import com.xwder.app.consts.DataBaseConstants;
import com.xwder.app.utils.ObjectUtil;
import com.xwder.app.utils.SpringUtils;
import com.xwder.app.utils.StringUtils;

import java.util.Map;

/**
 * @author wande
 * @version 1.0
 * @date 2020/08/05
 */
public class DAOHelper {


    /**
     * @return
     */
    private static Map<String, String> getCache() {
        return BaseDaoResourceHandler.DAO_RESOURCE_CACHE;
    }

    /**
     * @param classPath
     * @param sqlId
     * @param dbType
     * @return
     */
    private static String buildCacheKey(String classPath, String sqlId, String dbType) {
        return "[NAMESPACE:" + classPath + "]" + "[SQLID:" + sqlId + "]" + "[DBTYPE:" + dbType + "]";
    }

    /**
     * <p>Get sql sentence of daoMapper.xml under classpath by class that extends BaseDaoResourceHandler and sql id.</p>
     *
     * @param clazz
     * @param sqlId
     * @return
     */
    public static String getSQL(Class<? extends BaseDaoResourceHandler> clazz, String sqlId) {
        String classPath = ObjectUtil.getSlashClassPath(clazz);
        String dbType = SpringUtils.getBean(SysConfigAttribute.class).getSysDbType();

        String strSql = DAOHelper.getCache().get(buildCacheKey(classPath, sqlId, dbType));

        if (StringUtils.isEmpty(strSql)) {
            strSql = DAOHelper.getCache().get(buildCacheKey(classPath, sqlId, DataBaseConstants.DBTYPE_COMMON));
        }

        return strSql;
    }

    /**
     * <p>Add or update sql sentence to daoMapper.xml under classpath by class,sql id, db type and sql sentence.</p>
     *
     * @param clazz
     * @param sqlId
     * @param dbType
     * @param strSql
     */
    public static void putSQL(Class clazz, String sqlId, String dbType, String strSql) {
        String classPath = ObjectUtil.getSlashClassPath(clazz);

        DAOHelper.getCache().put(buildCacheKey(classPath, sqlId, dbType), strSql);
    }

}


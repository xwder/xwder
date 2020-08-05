package com.xwder.app.utils;


/**
 * @author wande
 * @version 1.0
 * @date 2020/08/05
 */
public class ObjectUtil {

    /**
     * <p>Get Class Path String By Class.</p>
     *
     * @param clazz
     * @return
     */
    public static String getSlashClassPath(Class clazz) {
        return StringUtils.replace(StringUtils.substring(clazz.getName(), 0,
                clazz.getName().indexOf(clazz.getSimpleName()))
                , StringUtils.DOT, StringUtils.SLASH_FORWARD);
    }
}

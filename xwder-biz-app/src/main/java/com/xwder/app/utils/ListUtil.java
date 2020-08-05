package com.xwder.app.utils;

import java.util.List;

/**
 * @author wande
 * @version 1.0
 * @date 2020/08/05
 */
public class ListUtil {

    public static boolean isEmpty(List list) {
        return list == null || list.isEmpty();
    }

    public static boolean isNotEmpty(List list) {
        return !ListUtil.isEmpty(list);
    }

    public static <T> T get(List<T> list, int index) {
        if (ListUtil.isEmpty(list)) {
            return null;
        } else {
            return list.get(index);
        }
    }
}

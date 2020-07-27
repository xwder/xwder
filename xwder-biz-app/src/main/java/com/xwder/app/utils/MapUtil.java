package com.xwder.app.utils;

import com.google.common.collect.Maps;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * map 工具类
 *
 * @author wande
 * @version 1.0
 * @date 2020/07/27
 */
public class MapUtil {

    private static Pattern linePattern = Pattern.compile("_(\\w)");
    private static Pattern humpPattern = Pattern.compile("[A-Z]");

    /**
     * 处理map中 包含下划线以及is_开头的key
     *
     * @param map
     * @return
     */
    public static Map<String, Object> convertMap(Map<String, Object> map) {
        HashMap<String, Object> resultMap = Maps.newHashMap();
        if (map == null || map.size() < 1) {
            return Maps.newHashMap();
        }
        for (String key : map.keySet()) {
            String tempKey = key;
            if (key.toLowerCase().startsWith("is_")) {
                tempKey = key.replaceFirst("is_", "");
            }
            tempKey = lineToHump(tempKey);
            resultMap.put(tempKey, map.get(key));
        }
        return resultMap;
    }

    /**
     * 下划线转驼峰
     */
    public static String lineToHump(String str) {
        str = str.toLowerCase();
        Matcher matcher = linePattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }


    /**
     * 驼峰转下划线,效率比上面高
     */
    public static String humpToLine2(String str) {
        Matcher matcher = humpPattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }
}

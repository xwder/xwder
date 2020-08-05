package com.xwder.app.helper.dao;

import com.xwder.app.utils.StringUtils;
import org.hibernate.transform.ResultTransformer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 将查询的结果转换为Map，并对数据库的命名去掉_,进行驼峰转换，如：name_cn => nameCn
 *
 * @author wande
 * @version 1.0
 * @date 2020/08/05
 */
public class MapResultTransformer implements ResultTransformer {
    public static MapResultTransformer INSTANSE = new MapResultTransformer();

    private MapResultTransformer() {

    }

    @Override
    public Object transformTuple(Object[] objects, String[] aliases) {
        Map result = new HashMap(objects.length);

        for (int i = 0; i < objects.length; ++i) {
            String alias = StringUtils.camel(aliases[i]);
            if (alias != null) {
                result.put(alias, objects[i]);
            }
        }

        return result;
    }

    @Override
    public List transformList(List list) {
        return list;
    }
}


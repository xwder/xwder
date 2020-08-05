package com.xwder.app.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;
import java.util.Map;

/**
 * @author xwder
 * @version 1.0
 */
public class JSONUtil {
    private static Log logger = LogFactory.getLog(JSONUtil.class);

    /**
     * <p>Object(bean、map、list) to JSON string, supports multi level.</p>
     *
     * @param obj
     * @return
     */
    public static String format(Object obj) {
        if (obj != null) {
            return JSON.toJSONString(obj, SerializerFeature.WriteMapNullValue,
                    SerializerFeature.UseISO8601DateFormat,
                    SerializerFeature.PrettyFormat,
                    SerializerFeature.DisableCircularReferenceDetect);
        } else {
            return StringUtils.EMPTY;
        }
    }

    /**
     * <p>JSON string to Map.</p>
     *
     * @param jsonStr
     * @return
     */
    public static Map parseMap(String jsonStr) {
        return JSON.parseObject(jsonStr, Feature.AllowISO8601DateFormat);
    }

    /**
     * <p>JSON string to List.</p>
     *
     * @param jsonStr
     * @return
     */
    public static List parseList(String jsonStr) {
        return JSON.parseArray(jsonStr);
    }

    /**
     * <p>JSON string to Bean.</p>
     *
     * @param jsonStr
     * @return
     */
    public static <T> T parseBean(String jsonStr, Class<T> beanClass) {
        return JSON.parseObject(jsonStr, beanClass);
    }

    /**
     * <p>JSON string to BeanList.</p>
     *
     * @param jsonStr
     * @return
     */
    public static <T> List<T> parseBeanList(String jsonStr, Class<T> beanClass) {
        return JSON.parseArray(jsonStr, beanClass);
    }
}

package com.xwder.cloud.commmon.utils;

import lombok.extern.slf4j.Slf4j;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class BeanUtil {

    /**
     * 把map对象转换为javabean
     *
     * @param map
     * @param beantype
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> T map2bean(Map<String, Object> map, Class<T> beantype) {
        try {
            //创建对象
            T object = beantype.newInstance();
            //获取类的属性描述器
            BeanInfo beaninfo = Introspector.getBeanInfo(beantype, Object.class);
            //获取类的属性集
            PropertyDescriptor[] pro = beaninfo.getPropertyDescriptors();
            for (PropertyDescriptor property : pro) {
                //获取属性的名字
                String name = property.getName();
                //得到属性name在map中对应的value。
                Object value = map.get(name);
                //得到属性的set方法
                Method set = property.getWriteMethod();
                //接下来将map的value转换为属性的value
                set.invoke(object, value);//执行set方法
            }
        } catch (Exception e) {
            log.error("转换 [{}] to bean [{}] 失败", map.toString(), beantype.toString());
        }

        return null;
    }

    /**
     * 将javabean转换为map
     *
     * @param bean
     * @param isNull null值属性是否加入map中
     * @return
     * @throws Exception
     */
    public static Map<String, Object> bean2map(Object bean, boolean isNull) {
        Map<String, Object> map = new HashMap<>();
        try {
            //获取类的属性描述器
            BeanInfo beaninfo = Introspector.getBeanInfo(bean.getClass(), Object.class);
            //获取类的属性集
            PropertyDescriptor[] pro = beaninfo.getPropertyDescriptors();
            for (PropertyDescriptor property : pro) {
                String key = property.getName();
                Method get = property.getReadMethod();
                //执行get方法得到属性的值
                Object value = get.invoke(bean);
                if (isNull) {
                    map.put(key, value);
                } else if (!isNull && value != null) {
                    map.put(key, value);
                }

            }
        } catch (Exception e) {
            log.error("转换 [{}] to map 失败", bean.toString());
        }
        return map;
    }
}

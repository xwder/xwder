package com.xwder.app.helper.dao;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import com.xwder.app.utils.JSONUtil;
import com.xwder.app.utils.StringUtils;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.property.access.internal.PropertyAccessStrategyBasicImpl;
import org.hibernate.property.access.internal.PropertyAccessStrategyChainedImpl;
import org.hibernate.property.access.internal.PropertyAccessStrategyFieldImpl;
import org.hibernate.property.access.internal.PropertyAccessStrategyMapImpl;
import org.hibernate.property.access.spi.PropertyAccessStrategy;
import org.hibernate.property.access.spi.Setter;
import org.hibernate.transform.ResultTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 将查询的结果转换为Dto对象，并对数据库的命名去掉_,进行驼峰转换，如：name_cn => nameCn
 * 如果sql中的字段dto中没有，会自动忽略该字段
 *
 * @author wande
 * @version 1.0
 * @date 2020/08/05
 */
public class DtoResultTransformer implements ResultTransformer {

    private static final Logger log = LoggerFactory.getLogger(DtoResultTransformer.class);
    private static final String GET = "get";
    private static final String SET = "set";
    private static final String IS = "is";

    Class dtoClass;
    private boolean isInitialized;
    private String[] aliases;
    private Setter[] setters;
    private HashSet<String> fields;

    public DtoResultTransformer(Class dtoClass) {
        this.dtoClass = dtoClass;
    }

    private void initialize(String[] aliases) {
        PropertyAccessStrategyChainedImpl propertyAccessStrategy = new PropertyAccessStrategyChainedImpl(
                new PropertyAccessStrategy[]{
                        PropertyAccessStrategyBasicImpl.INSTANCE,
                        PropertyAccessStrategyFieldImpl.INSTANCE,
                        PropertyAccessStrategyMapImpl.INSTANCE});

        this.aliases = new String[aliases.length];
        this.setters = new Setter[aliases.length];
        this.fields = new HashSet<>(50);
        initFields();

        for (int i = 0; i < aliases.length; ++i) {
            String alias = StringUtils.camel(aliases[i]);
            if (alias != null) {
                this.aliases[i] = alias;
                //判断实体是否存在该字段，存在设置值，否则忽略
                if (fields.contains(alias)) {
                    this.setters[i] = propertyAccessStrategy.buildPropertyAccess(this.dtoClass, alias).getSetter();
                }
            }
        }

        this.isInitialized = true;
    }

    private void initFields() {
        Method[] methods = dtoClass.getMethods();
        Map<String, Integer> getSetMap = new HashMap<>(50);
        for (int i = 0; i < methods.length; i++) {
            if (isGetSet(methods[i])) {
                String fieldName = getFiledName(methods[i]);
                if (getSetMap.containsKey(fieldName)) {
                    getSetMap.put(fieldName, 2);
                } else {
                    getSetMap.put(fieldName, 1);
                }
            }
        }
        getSetMap.forEach((k, v) -> {
            if (v == 2) {
                fields.add(k);
            }
        });
    }

    /**
     * 判断方法是否为pojo的get set
     *
     * @param method
     * @return
     */
    private boolean isGetSet(Method method) {
        if (method.getModifiers() != Modifier.PUBLIC) {
            return false;
        }

        if (method.getName().startsWith(GET)
                || method.getName().startsWith(SET)
                || method.getName().startsWith(IS)) {
            return true;
        }

        return false;
    }

    /**
     * 根据方法，获取filed的名字
     *
     * @param method
     * @return
     */
    private String getFiledName(Method method) {
        String field = StringUtils.EMPTY;
        if (method.getName().startsWith(GET) || method.getName().startsWith(SET)) {
            field = method.getName().substring(3);
        } else if (method.getName().startsWith(IS)) {
            field = method.getName().substring(2);
        }
        return StringUtils.getFirstLower(field);
    }

    @Override
    public Object transformTuple(Object[] objects, String[] aliases) {
        if (log.isDebugEnabled()) {
            log.debug("transformTuple objects is:" + JSONUtil.format(objects));
            log.debug("transformTuple strings is:" + JSONUtil.format(aliases));
        }

        // 保证获取pojo信息只获取一次
        if (!this.isInitialized) {
            this.initialize(aliases);
        }

        Object dto = null;
        try {
            dto = this.dtoClass.newInstance();
            for (int i = 0; i < aliases.length; ++i) {
                if (this.setters[i] != null) {
                    objects[i] = adapterValue(objects[i], setters[i]);
                    this.setters[i].set(dto, objects[i], (SessionFactoryImplementor) null);
                }
            }
        } catch (InstantiationException | IllegalAccessException e) {
            log.error("transformTuple method,setter field error!", e);
        }
        return dto;
    }

    private Object adapterValue(Object value, Setter setter) {
        if (value == null) {
            return null;
        }

        if (setter.getMethod().getGenericParameterTypes()[0].getTypeName().equals("java.lang.Long")
                && value instanceof BigInteger) {
            return ((BigInteger) value).longValue();
        } else if (setter.getMethod().getGenericParameterTypes()[0].getTypeName().equals("java.lang.Long")
                && value instanceof Integer) {
            return ((Integer) value).longValue();
        }
        return value;
    }

    @Override
    public List transformList(List list) {
        if (log.isInfoEnabled()) {
            log.debug("transformList list is:" + JSONUtil.format(list));
        }

        return list;
    }
}

package com.xwder.app.common.jpa.annotation;

import com.xwder.app.common.jpa.enumobj.QueryType;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author xwder
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface QueryField {
    QueryType type() default QueryType.EQUAL;

    String name() default "";

    boolean nullAble() default true;
}
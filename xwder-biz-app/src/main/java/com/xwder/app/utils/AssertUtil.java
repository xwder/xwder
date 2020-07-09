package com.xwder.app.utils;

import cn.hutool.core.util.StrUtil;
import com.xwder.app.advice.BizException;
import com.xwder.cloud.commmon.api.ResultCode;

/**
 * 参考 hutool Assert
 * 断言<br>
 * 断言某些对象或值是否符合规定，否则抛出异常。经常用于做变量检查
 *
 * @author xwder
 */
public class AssertUtil {

    /**
     * 断言对象是否为{@code null} ，如果不为{@code null} 抛出{@link BizException} 异常
     *
     * <pre class="code">
     * Assert.isNull(value, "The value must be null");
     * </pre>
     *
     * @param object           被检查的对象
     * @param code             被检查的对象
     * @param errorMsgTemplate 消息模板，变量使用{}表示
     * @param params           参数列表
     * @throws BizException if the object is not {@code null}
     */
    public static void isNull(Object object, Integer code, String errorMsgTemplate, Object... params) throws BizException {
        if (object != null) {
            throw new BizException(code, StrUtil.format(errorMsgTemplate, params));
        }
    }

    /**
     * 断言对象是否为{@code null} ，如果为{@code null} 抛出{@link BizException} 异常
     *
     * <pre class="code">
     * Assert.isNull(value, "The value must be null");
     * </pre>
     *
     * @param object           被检查的对象
     * @param code             异常响应码
     * @param errorMsgTemplate 消息模板，变量使用{}表示
     * @param params           参数列表
     * @throws BizException if the object is not {@code null}
     */
    public static void isNotNull(Object object, Integer code, String errorMsgTemplate, Object... params) throws BizException {
        if (object instanceof String && StrUtil.isEmpty((CharSequence) object)) {
            throw new BizException((int) ResultCode.PARAM_VALIDATE_FAILD.getCode(), StrUtil.format(errorMsgTemplate, params));
        }
        if (object == null) {
            throw new BizException(code, StrUtil.format(errorMsgTemplate, params));
        }
    }

    /**
     * 判断参数是否为null
     * 断言对象是否为{@code null} ，如果不为{@code null} 抛出{@link BizException} 异常
     *
     * <pre class="code">
     * Assert.isNull(value, "The value must be null");
     * </pre>
     *
     * @param object           被检查的对象
     * @param errorMsgTemplate 消息模板，变量使用{}表示
     * @param params           参数列表
     * @throws BizException if the object is not {@code null}
     */
    public static void paramIsNull(Object object, String errorMsgTemplate, Object... params) throws BizException {
        if (object != null) {
            throw new BizException((int) ResultCode.PARAM_VALIDATE_FAILD.getCode(), StrUtil.format(errorMsgTemplate, params));
        }
    }


    /**
     * 判断参数是否不为null
     * 断言对象是否为{@code null} ，如果为{@code null} 抛出{@link BizException} 异常
     *
     * <pre class="code">
     * Assert.isNull(value, "The value must be null");
     * </pre>
     *
     * @param object           被检查的对象
     * @param errorMsgTemplate 消息模板，变量使用{}表示
     * @param params           参数列表
     * @throws BizException if the object is not {@code null}
     */
    public static void paramIsNotNull(Object object, String errorMsgTemplate, Object... params) throws BizException {
        if (object instanceof String && StrUtil.isEmpty((CharSequence) object)) {
            throw new BizException((int) ResultCode.PARAM_VALIDATE_FAILD.getCode(), StrUtil.format(errorMsgTemplate, params));
        }

        if (object == null) {
            throw new BizException((int) ResultCode.PARAM_VALIDATE_FAILD.getCode(), StrUtil.format(errorMsgTemplate, params));
        }
    }


    /**
     * 断言对象是否为{@code true} ，如果为{@code true} 抛出{@link BizException} 异常
     *
     * <pre class="code">
     * Assert.isNull(value, "The value must be null");
     * </pre>
     *
     * @param expression       被检查的对象
     * @param code             被检查的对象
     * @param errorMsgTemplate 消息模板，变量使用{}表示
     * @param params           参数列表
     * @throws BizException if the object is not {@code null}
     */
    public static void isTrue(boolean expression, Integer code, String errorMsgTemplate, Object... params) throws BizException {
        if (expression) {
            throw new BizException(code, StrUtil.format(errorMsgTemplate, params));
        }
    }

    /**
     * 断言对象是否为{@code true} ，如果不为{@code true} 抛出{@link BizException} 异常
     *
     * <pre class="code">
     * Assert.isNull(value, "The value must be null");
     * </pre>
     *
     * @param expression       被检查的对象
     * @param code             被检查的对象
     * @param errorMsgTemplate 消息模板，变量使用{}表示
     * @param params           参数列表
     * @throws BizException if the object is not {@code null}
     */
    public static void isNotTrue(boolean expression, Integer code, String errorMsgTemplate, Object... params) throws BizException {
        if (!expression) {
            throw new BizException(code, StrUtil.format(errorMsgTemplate, params));
        }
    }


    /**
     * 校验参数
     * 断言对象是否为{@code true} ，如果为{@code true} 抛出{@link BizException} 异常
     *
     * <pre class="code">
     * Assert.isNull(value, "The value must be null");
     * </pre>
     *
     * @param expression       被检查的对象
     * @param errorMsgTemplate 消息模板，变量使用{}表示
     * @param params           参数列表
     * @throws BizException if the object is not {@code null}
     */
    public static void paramIsTrue(boolean expression, String errorMsgTemplate, Object... params) throws BizException {
        if (expression) {
            throw new BizException((int) ResultCode.PARAM_VALIDATE_FAILD.getCode(), StrUtil.format(errorMsgTemplate, params));
        }
    }

    /**
     * 校验参数
     * 断言对象是否为{@code true} ，如果不为{@code true} 抛出{@link BizException} 异常
     *
     * <pre class="code">
     * Assert.isNull(value, "The value must be null");
     * </pre>
     *
     * @param expression       被检查的对象
     * @param errorMsgTemplate 消息模板，变量使用{}表示
     * @param params           参数列表
     * @throws BizException if the object is not {@code null}
     */
    public static void paramIsNotTrue(boolean expression, String errorMsgTemplate, Object... params) throws BizException {
        if (!expression) {
            throw new BizException((int) ResultCode.PARAM_VALIDATE_FAILD.getCode(), StrUtil.format(errorMsgTemplate, params));
        }
    }
}

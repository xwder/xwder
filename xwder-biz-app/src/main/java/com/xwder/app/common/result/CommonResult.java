package com.xwder.app.common.result;

import lombok.Data;

/**
 * 通用返回对象
 *
 * @author wande
 * @version 1.0
 * @date 2019/12/25
 */
@Data
public class CommonResult<T> {
    private long code;
    private String message;
    private T data;

    protected CommonResult() {
    }

    protected CommonResult(long code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 成功返回结果
     *
     * @return
     */
    public static <T> CommonResult<T> success() {
        return new CommonResult<T>(ResultCode.SUCCESS.getCode(), "操作成功", null);
    }

    /**
     * 成功返回结果
     *
     * @param data 获取的数据
     * @param <T>
     * @return
     */
    public static <T> CommonResult<T> success(T data) {
        return new CommonResult<T>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), data);
    }

    /**
     * 成功返回结果
     *
     * @param data    获取的数据
     * @param message 提示信息
     * @param <T>
     * @return
     */
    public static <T> CommonResult<T> success(T data, String message) {
        return new CommonResult<T>(ResultCode.SUCCESS.getCode(), message, data);
    }

    /**
     * 成功返回结果
     *
     * @param code    code
     * @param message 提示信息
     * @param <T>
     * @return
     */
    public static <T> CommonResult<T> success(long code, String message) {
        return new CommonResult<T>(code, message, null);
    }

    /**
     * 失败返回结果
     *
     * @param errorCode 错误码
     * @param <T>
     * @return
     */
    public static <T> CommonResult<T> failed(IErrorCode errorCode) {
        return new CommonResult<T>(errorCode.getCode(), errorCode.getMessage(), null);
    }

    /**
     * 失败返回结果
     *
     * @param code 错误码
     * @param <T>
     * @return
     */
    public static <T> CommonResult<T> failed(long code) {
        return new CommonResult<T>(code, null, null);
    }


    /**
     * 失败返回结果
     *
     * @param code 错误码
     * @param msg  错误消息
     * @param <T>
     * @return
     */
    public static <T> CommonResult<T> failed(long code, String msg) {
        return new CommonResult<T>(code, msg, null);
    }


    /**
     * 失败返回结果
     *
     * @param message 提示信息
     * @param <T>
     * @return
     */
    public static <T> CommonResult<T> failed(String message) {
        return new CommonResult<T>(ResultCode.FAILED.getCode(), message, null);
    }


    /**
     * 失败返回结果
     *
     * @param <T>
     * @return
     */
    public static <T> CommonResult<T> failed() {
        return failed(ResultCode.FAILED);
    }

    /**
     * 参数验证失败返回结果
     *
     * @param <T>
     * @return
     */
    public static <T> CommonResult<T> validateFailed() {
        return failed(ResultCode.VALIDATE_FAILED);
    }


    /**
     * 参数验证失败返回结果
     *
     * @param message 提示信息
     * @param <T>
     * @return
     */
    public static <T> CommonResult<T> validateFailed(String message) {
        return new CommonResult<T>(ResultCode.VALIDATE_FAILED.getCode(), message, null);
    }

    /**
     * 未登录返回结果
     *
     * @param data
     * @param <T>
     * @return
     */
    public static <T> CommonResult<T> unauthorized(T data) {
        return new CommonResult<T>(ResultCode.UNAUTHORIZED.getCode(), ResultCode.UNAUTHORIZED.getMessage(), data);
    }


    /**
     * 未授权返回结果
     *
     * @param data
     * @param <T>
     * @return
     */
    public static <T> CommonResult<T> forbidden(T data) {
        return new CommonResult<T>(ResultCode.FORBIDDEN.getCode(), ResultCode.FORBIDDEN.getMessage(), data);
    }


    /**
     * 未授权返回结果
     *
     * @param data
     * @param <T>
     * @return
     */
    public static <T> CommonResult<T> commonResult(long code, String msg, T data) {
        return new CommonResult<T>(code, msg, data);
    }


}

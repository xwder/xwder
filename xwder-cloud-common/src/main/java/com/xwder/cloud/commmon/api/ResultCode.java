package com.xwder.cloud.commmon.api;

/**
 * 枚举了一些常用API操作码
 *
 * @author wande
 * @version 1.0
 * @date 2019/12/25
 */
public enum ResultCode implements IErrorCode {
    SUCCESS(200, "操作成功"),
    FAILED(500, "操作失败"),
    VALIDATE_FAILED(404, "服务器找不到请求"),
    UNAUTHORIZED(401, "暂未登录或token已经过期"),
    FORBIDDEN(403, "没有相关权限"),
    PARAM_VALIDATE_FAILD(405, "参数校验失败");
    private long code;
    private String message;

    private ResultCode(long code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public long getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}

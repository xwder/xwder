package com.xwder.manage.common.utils.message;

/**
 * @ClassName: ResultEnum
 * @Description:
 * @Author: xwder
 * @Date: 2019年7月9日20:58:58
 * @Version: 1.0
 */
public enum ResultEnum {
    UNKNOWN_ERROR(-1, "未知错误"),
    SUCCESS(10000, "成功"),
    USER_NOT_EXIST(1, "用户不存在"),
    USER_IS_EXISTS(2, "用户已存在"),
    DATA_IS_NULL(3, "数据为空"),
    USER_NAME_OR_PASSWORD_IS_NULL(4, "用户名或者密码为空"),
    USER_PASSWORD_INCORRECT(5, "密码错误"),
    USER_ACCOUNT_LOCKED(6, "用户已被锁定"),
    USER_CHECK_ERROR(7, "用户验证失败");

    private Integer code;
    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}

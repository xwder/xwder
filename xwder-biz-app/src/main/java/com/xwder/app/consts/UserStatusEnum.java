package com.xwder.app.consts;

/**
 * 用户状态枚举类
 * 账号状态 0已注册未验证，
 * 1验证邮箱，
 * 2验证了手机，
 * 3验证了手机加邮箱
 */
public enum UserStatusEnum {

    UN_VERIFY(0, "0已注册未验证"),
    EMAIL_VERIFY(1, "1验证邮箱"),
    PHONE_VERIFY(2, "2验证手机"),
    ALL_VERIFY(3, "3验证手机加邮箱");

    private Integer code;
    private String desc;

    UserStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}

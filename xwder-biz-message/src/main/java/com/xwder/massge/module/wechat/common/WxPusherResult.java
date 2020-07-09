package com.xwder.massge.module.wechat.common;

/**
 * wxpusher
 * 返回编码,参考http语义
 */
public enum WxPusherResult {
    SUCCESS(1000, "发送成功"),
    NOT_FOLLOW(1000, "未关注该应用"),
    BIZ_FAIL(1001, "业务异常错误"),
    UNAUTHORIZED(1002, "未认证"),
    SIGN_FAIL(1003, "签名错误"),
    NOT_FOUND(1004, "接口不存在"),
    INTERNAL_SERVER_ERROR(1005, "服务器内部错误"),
    WEIXIN_ERROR(1006, "微信交互的过程中发生异常"),
    BIZ_WAIT_SCAN_LOGIN(10000, "等待用户扫码登录"),
    ERROR(500, "未知错误");

    private final Integer code;
    private final String msg;

    WxPusherResult(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    /**
     * 根据code返回枚举对象
     *
     * @param value
     * @return
     */
    public static WxPusherResult getWxPusherResult(Integer value) {
        WxPusherResult[] wxPusherResults = values();
        for (WxPusherResult wxPusherResult : wxPusherResults) {
            if (wxPusherResult.getCode().equals(value)) {
                return wxPusherResult;
            }
        }
        return ERROR;
    }
}
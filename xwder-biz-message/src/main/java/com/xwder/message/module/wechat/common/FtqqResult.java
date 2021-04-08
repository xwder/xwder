package com.xwder.message.module.wechat.common;

/**
 * server酱 错误消息枚举
 *
 * @Author: xwder
 * @Date: 2019/11/12 22:54
 * @Description:
 */
public enum FtqqResult {

    SUCCESS(0, "发送成功"),
    REPEAT_ERROR(1024, "不要重复发送同样的内容"),
    ERROR(500, "未知错误");

    private Integer code;
    private String msg;

    private FtqqResult(Integer code, String msg) {
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
    public static FtqqResult getFtqqResult(Integer value) {
        FtqqResult[] ftqqResults = values();
        for (FtqqResult ftqqResult : ftqqResults) {
            if (ftqqResult.getCode().equals(value)) {
                return ftqqResult;
            }
        }
        return ERROR;
    }
}

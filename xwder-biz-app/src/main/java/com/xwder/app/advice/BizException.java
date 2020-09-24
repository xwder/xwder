package com.xwder.app.advice;

import com.xwder.app.common.result.ResultCode;
import lombok.Getter;

/**
 * 自定制异常类
 *
 * @author xwder
 * @date 2020年7月9日
 */
@Getter
public class BizException extends RuntimeException {
    private long code;
    private String message;

    public BizException(long code, String message) {
        this.code = code;
        this.message = message;
    }

    public BizException(ResultCode resultCodeEnum) {
        this.code = (int) resultCodeEnum.getCode();
        this.message = resultCodeEnum.getMessage();
    }
}

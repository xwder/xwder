package com.xwder.app.advice;

import com.baomidou.kaptcha.exception.KaptchaException;
import com.baomidou.kaptcha.exception.KaptchaIncorrectException;
import com.baomidou.kaptcha.exception.KaptchaNotFoundException;
import com.baomidou.kaptcha.exception.KaptchaTimeoutException;
import com.xwder.cloud.commmon.api.CommonResult;
import com.xwder.cloud.commmon.api.ResultCode;
import com.xwder.cloud.commmon.exception.MyException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * 统一异常处理
 */
@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {

    /**
     * 全局异常处理
     *
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    public CommonResult defaultException(HttpServletRequest request, Exception e) {
        log.error("{}", e);
        return CommonResult.failed(e.getMessage());

    }

    /**
     * 自定义异常处理
     *
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(value = MyException.class)
    public CommonResult myException(HttpServletRequest request, MyException e) {
        Integer code = e.getCode();
        String message = e.getMessage();
        log.error("{}", e);
        return CommonResult.failed(code, message);
    }

    /**
     * 自定义业务异常
     *
     * @param bizException
     * @return
     */
    @ExceptionHandler(BizException.class)
    public CommonResult handleCustomException(BizException bizException) {
        log.info("{}", bizException.getMessage());
        return CommonResult.failed(bizException.getCode(), bizException.getMessage());
    }

    /**
     * 验证码错误
     *
     * @param kaptchaException
     * @return
     */
    @ExceptionHandler(value = KaptchaException.class)
    public CommonResult kaptchaExceptionHandler(KaptchaException kaptchaException) {
        String errMsg = "";
        if (kaptchaException instanceof KaptchaIncorrectException) {
            errMsg = "验证码不正确";
        } else if (kaptchaException instanceof KaptchaNotFoundException) {
            errMsg = "验证码未找到";
        } else if (kaptchaException instanceof KaptchaTimeoutException) {
            errMsg = "验证码过期";
        } else {
            errMsg = "验证码渲染失败";
        }
        log.info("验证码验证失败-{}", errMsg);
        return CommonResult.failed(ResultCode.PARAM_VALIDATE_FAILD.getCode(), errMsg);
    }

}
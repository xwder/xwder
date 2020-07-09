package com.xwder.app.advice;

import com.xwder.cloud.commmon.api.CommonResult;
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

}
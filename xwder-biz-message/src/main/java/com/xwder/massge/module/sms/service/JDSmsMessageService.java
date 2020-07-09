package com.xwder.massge.module.sms.service;

import com.xwder.cloud.commmon.api.CommonResult;

/**
 * @Author: xwder
 * @Date: 2019/7/25 23:27
 * @Description:
 */
public interface JDSmsMessageService {

    /**
     *  JD 短信api 不能立即到达
     * 单个用户发送短信
     * @param phone 电话
     * @param content 短信内容 不能有 【】 []
     * @return
     */
    public CommonResult sendJDSmsMessage(String phone, String content);
}

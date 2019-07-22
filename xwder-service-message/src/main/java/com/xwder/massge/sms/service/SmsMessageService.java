package com.xwder.massge.sms.service;

import com.xwder.framework.utils.message.Result;

/**
 * 短信
 *
 * @Author: xwder
 * @Date: 2019/7/20 00:12
 * @Description:
 */
public interface SmsMessageService {

    /**
     * 单个用户发送短信
     * @param phone 电话
     * @param content 短信内容 不能有 【】 []
     * @return
     */
    public Result sendSmsMessage(String phone, String content);

}

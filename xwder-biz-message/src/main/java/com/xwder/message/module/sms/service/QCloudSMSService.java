package com.xwder.message.module.sms.service;

import com.xwder.cloud.commmon.api.CommonResult;

/**
 * @Author: xwder
 * @Date: 2019/7/25 23:58
 * @Description:
 */
public interface QCloudSMSService {

    /**
     * 发送短信
     * @param phone
     * @param content
     * @return
     */
    CommonResult sendSMS(String phone, String content);

}

package com.xwder.massge.module.sms.service;

import com.xwder.framework.utils.message.Result;

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
    Result sendSMS(String phone,String content);

}

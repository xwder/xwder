package com.xwder.massge.module.sms.service;

import com.xwder.cloud.commmon.api.CommonResult;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

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

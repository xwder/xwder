package com.xwder.massge.module.sms.service;

import com.xwder.framework.utils.message.Result;

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
    Result sendSMS(String phone,String content) throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException;

}

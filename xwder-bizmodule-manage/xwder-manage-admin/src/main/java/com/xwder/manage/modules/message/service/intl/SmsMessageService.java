package com.xwder.manage.modules.message.service.intl;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public interface SmsMessageService {

    /**
     * 发送短信
     * @param phone
     * @param content
     * @return
     */
    boolean sendSMS(String phone, String content) throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException;

}

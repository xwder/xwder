package com.xwder.massge.module.sms.controller;

import com.xwder.cloud.commmon.api.CommonResult;
import com.xwder.massge.module.sms.service.QCloudSMSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * qcloud 发送即时短信
 * @Author: xwder
 * @Date: 2019/7/20 00:13
 * @Description:
 */
@RestController
@RequestMapping("/sms/qcloud")
public class QcloudSmsMessageController {

    @Autowired
    private QCloudSMSService qCloudSMSService;

    /**
     * 发送短信
     *
     * @param phone 手机号
     * @param content 短信内容
     * @return
     */
    @RequestMapping("/sendSmsMessage")
    public CommonResult sendQcloudSMS(String phone, String content) throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException {
        return qCloudSMSService.sendSMS(phone, content);
    }

}

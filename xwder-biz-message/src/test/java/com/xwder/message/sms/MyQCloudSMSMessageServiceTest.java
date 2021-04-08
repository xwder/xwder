package com.xwder.message.sms;

import com.xwder.cloud.commmon.api.CommonResult;
import com.xwder.message.XwderMessageApplication;
import com.xwder.message.module.sms.service.QCloudSMSService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * 短信测试类
 *
 * @Author: xwder
 * @Date: 2019/7/20 00:46
 * @Description:
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = XwderMessageApplication.class)
public class MyQCloudSMSMessageServiceTest {

    @Autowired
    private QCloudSMSService qCloudSMSService;

    /**
     * 发送短信测试
     */
    @Test
    public void sendSmsMessageTest() throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException {
//        String phone = "13509433172";
//        String phone = "18083024504";
        String phone = "17320447318";
        //String content = "您的手机号：13509433172，验证码：666666，请及时完成验证，如不是本人操作请忽略。【腾讯云市场】";
        String content = "您关注的 xxx 已更新，退订请点击 blog.xwder.com，链接五分钟内有效。【OneDay】";
        CommonResult result = qCloudSMSService.sendSMS(phone, content);
        System.out.println(result.toString());
    }
    /**
     * 18083024504
     * 发送短信测试
     */
    @Test
    public void sendSmsMessage2Test() throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException {
        String phone = "18083024504";
        //String content = "您的手机号：13509433172，验证码：666666，请及时完成验证，如不是本人操作请忽略。【腾讯云市场】";
        String content = "您关注的 剑来小说 已更新，退订请点击blog.xwder.com，链接五分钟内有效。【OneDay】";
        qCloudSMSService.sendSMS(phone, content);
    }

}

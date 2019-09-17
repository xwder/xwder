package com.xwder.message.sms;

import com.xwder.massge.MessageApplication;
import com.xwder.massge.module.sms.service.QCloudSMSService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 短信测试类
 *
 * @Author: xwder
 * @Date: 2019/7/20 00:46
 * @Description:
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MessageApplication.class)
public class MyQCloudSMSMessageServiceTest {

    @Autowired
    private QCloudSMSService qCloudSMSService;

    /**
     * 发送短信测试
     */
    @Test
    public void sendSmsMessageTest() {
        String phone = "13509433172";
        //String content = "您的手机号：13509433172，验证码：666666，请及时完成验证，如不是本人操作请忽略。【腾讯云市场】";
        String content = "您关注的 剑来 已更新，退订请点击blog.xwder.com，链接五分钟内有效。【OneDay】";
        qCloudSMSService.sendSMS(phone, content);
    }
    /**
     * 18083024504
     * 发送短信测试
     */
    @Test
    public void sendSmsMessage2Test() {
        String phone = "18083024504";
        //String content = "您的手机号：13509433172，验证码：666666，请及时完成验证，如不是本人操作请忽略。【腾讯云市场】";
        String content = "您关注的 剑来小说 已更新，退订请点击baidu.com，链接五分钟内有效。【OneDay】";
        //qCloudSMSService.sendSMS(phone, content);
    }

}

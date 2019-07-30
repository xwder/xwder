package com.xwder.message.sms;

import com.xwder.framework.utils.message.Result;
import com.xwder.massge.MessageApplication;
import com.xwder.massge.module.sms.service.impl.QCloudSMSServiceImpl;
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
    private QCloudSMSServiceImpl qCloudSMSService;

    /**
     * 发送短信测试
     */
    @Test
    public void sendSmsMessageTest() {
        String phone = "13509433172";
        String content = "您的手机号：13509433172，验证码：666666，请及时完成验证，如不是本人操作请忽略。【腾讯云市场】";
        Result result = null;
        qCloudSMSService.sendSMS(phone, content);
        System.out.println(result);
    }

}

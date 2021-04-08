package com.xwder.message.sms;

import com.xwder.message.XwderMessageApplication;
import com.xwder.message.module.sms.service.JDSmsMessageService;
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
@SpringBootTest(classes = XwderMessageApplication.class)
public class JDSmsMessageServiceTest {

    @Autowired
    private JDSmsMessageService smsMessageService;

    /**
     * 发送短信测试
     */
    @Test
    public void sendSmsMessageTest(){
        String phone = "13509433172";
        String content = "您关注的内容已更新";
        //Result result = smsMessageService.sendJDSmsMessage(phone,content);
        //System.out.println(result);
    }

}

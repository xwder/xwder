package com.xwder.message.wechat;

import com.xwder.framework.utils.message.Result;
import com.xwder.massge.MessageApplication;
import com.xwder.massge.module.sms.service.JDSmsMessageService;
import com.xwder.massge.module.wechat.service.intf.FtqqWeChatMessageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Server酱发送微信消息测试
 *
 * @Author: xwder
 * @Date: 2019-11-12 23:10:40
 * @Description:
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MessageApplication.class)
public class FtqqWeChatMessageServiceTest {

    @Autowired
    private FtqqWeChatMessageService ftqqWeChatMessageService;

    /**
     * Server酱发送微信消息测试
     */
    @Test
    public void sendFtqqWeChatMessageTest() {
        String msg = "您关注的内容已更新";
        Result result = ftqqWeChatMessageService.sendStrMessage(msg);
    }

}

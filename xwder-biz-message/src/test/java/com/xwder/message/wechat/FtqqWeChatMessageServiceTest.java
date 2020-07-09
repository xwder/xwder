package com.xwder.message.wechat;

import com.xwder.cloud.commmon.api.CommonResult;
import com.xwder.massge.XwderMessageApplication;
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
@SpringBootTest(classes = XwderMessageApplication.class)
public class FtqqWeChatMessageServiceTest {

    @Autowired
    private FtqqWeChatMessageService ftqqWeChatMessageService;

    /**
     * Server酱发送微信消息测试
     */
    @Test
    public void sendFtqqWeChatMessageTest() {
        String msg = "您关注的内容已更新";
        CommonResult result = ftqqWeChatMessageService.sendStrMessage(msg);
    }

}

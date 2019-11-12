package com.xwder.message.wechat;

import com.xwder.framework.utils.message.Result;
import com.xwder.massge.MessageApplication;
import com.xwder.massge.module.wechat.service.intf.FtqqWeChatMessageService;
import com.xwder.massge.module.wechat.service.intf.WxPusherWeChatMessageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Wxpusher 发送微信消息测试
 *
 * @Author: xwder
 * @Date: 2019-11-12 23:41:20
 * @Description:
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MessageApplication.class)
public class WxPusherWeChatMessageServiceTest {

    @Autowired
    private WxPusherWeChatMessageService wxPusherWeChatMessageService;

    /**
     * Wxpusher 发送微信字符串消息测试
     */
    @Test
    public void sendWxPusherChatStrMessageTest() {
        String msg = "您关注的剑来已经更新";
        String uid = "UID_3izJBf0A7BG2CsQ8bdaBjnm4JKho";
        Result result = wxPusherWeChatMessageService.sendStrMessage(uid,msg);
    }

    /**
     * Wxpusher 发送微信Html消息测试
     */
    @Test
    public void sendWxPusherChatHtmlMessageTest() {
        String content = "<a href=\"xwder.com\">";
        String uid = "UID_3izJBf0A7BG2CsQ8bdaBjnm4JKho";
        Result result = wxPusherWeChatMessageService.sendHtmlMessage(uid,content);
    }

    /**
     * Wxpusher 发送微信MakrDown消息测试
     */
    @Test
    public void sendWxPusherChatMarkDownMessageTest() {
        String content = "### xwder";
        String uid = "UID_3izJBf0A7BG2CsQ8bdaBjnm4JKho";
        Result result = wxPusherWeChatMessageService.sendMarkDownMessage(uid,content);
    }

}

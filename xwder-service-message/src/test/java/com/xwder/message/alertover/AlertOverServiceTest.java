package com.xwder.message.alertover;

import com.xwder.framework.utils.message.Result;
import com.xwder.massge.MessageApplication;
import com.xwder.massge.module.alertover.service.inft.AlertOverMessageServie;
import com.xwder.massge.module.wechat.service.intf.FtqqWeChatMessageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * alertover 发送app消息推送
 *
 * @Author: xwder
 * @Date: 2019-11-19 20:10:40
 * @Description:
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MessageApplication.class)
public class AlertOverServiceTest {

    @Autowired
    private AlertOverMessageServie alertOverMessageServie;

    /**
     * alertover 发送app消息推送
     */
    @Test
    public void sendAlertOverMessageTest() {
        String title = "剑来";
        String content = "剑来";
        Result result = alertOverMessageServie.sendStrMessge(title,content);
    }

}

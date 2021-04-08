package com.xwder.message.alertover;

import com.xwder.cloud.commmon.api.CommonResult;
import com.xwder.message.XwderMessageApplication;
import com.xwder.message.module.alertover.service.inft.AlertOverMessageServie;
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
@SpringBootTest(classes = XwderMessageApplication.class)
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
        CommonResult result = alertOverMessageServie.sendStrMessge(title,content);
    }

}

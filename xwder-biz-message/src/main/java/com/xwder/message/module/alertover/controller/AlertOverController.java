package com.xwder.message.module.alertover.controller;

import com.xwder.cloud.commmon.api.CommonResult;
import com.xwder.message.module.alertover.service.inft.AlertOverMessageServie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: xwder
 * @Date: 2020/1/8 22:43
 * @Description:
 */
@RestController
@RequestMapping("/alertover")
public class AlertOverController {

    @Autowired
    private AlertOverMessageServie alertOverMessageServie;


    /**
     * 发送简单邮件
     *
     * @param title   主题
     * @param content 内容
     * @return
     */
    @RequestMapping("/sendAlertOverNotice")
    public CommonResult sendAlertOverNotice(String title, String content) {
        return alertOverMessageServie.sendStrMessge(title, content);
    }
}

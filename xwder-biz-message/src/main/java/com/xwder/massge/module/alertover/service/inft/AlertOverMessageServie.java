package com.xwder.massge.module.alertover.service.inft;

import com.xwder.cloud.commmon.api.CommonResult;


public interface AlertOverMessageServie {

    /**
     * alertover 消息推送
     * @param sendSource 发送源ID
     * @param receiveSource 接收组ID
     * @param title
     * @param content
     * @return
     */
    CommonResult sendStrMessgeWithSource(String sendSource, String receiveSource, String title, String content);

    /**
     *
     * @param title
     * @param content
     * @return
     */
    CommonResult sendStrMessge(String title, String content);

}

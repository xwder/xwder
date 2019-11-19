package com.xwder.massge.module.alertover.service.inft;

import com.xwder.framework.utils.message.Result;


public interface AlertOverMessageServie {

    /**
     * alertover 消息推送
     * @param sendSource 发送源ID
     * @param receiveSource 接收组ID
     * @param title
     * @param content
     * @return
     */
    Result sendStrMessgeWithSource(String sendSource, String receiveSource, String title, String content);

    /**
     *
     * @param title
     * @param content
     * @return
     */
    Result sendStrMessge(String title, String content);

}

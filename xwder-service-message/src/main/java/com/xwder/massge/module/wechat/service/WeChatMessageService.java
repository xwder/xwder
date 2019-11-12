package com.xwder.massge.module.wechat.service;

/**
 * 微信个人消息推送接口
 * @author xwder
 * @date   2019-11-12 18:36
 */
public interface WeChatMessageService {

    /**
     * 发送字符串消息
     * @param msg
     */
    void sendStrMessage(String msg);

    /**
     * 发送html消息
     * @param url
     */
    void sendHtmlMessage(String url);
}

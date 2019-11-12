package com.xwder.massge.module.wechat.service.intf;

import com.xwder.framework.utils.message.Result;

/**
 * Server酱 微信个人消息推送接口
 * @author xwder
 * @date   2019-11-12 18:36
 */
public interface FtqqWeChatMessageService {

    /**
     * 发送字符串消息
     * @param msg
     */
    Result sendStrMessage(String msg);

    /**
     * 发送html消息
     * @param url
     */
    Result sendHtmlMessage(String url);
}

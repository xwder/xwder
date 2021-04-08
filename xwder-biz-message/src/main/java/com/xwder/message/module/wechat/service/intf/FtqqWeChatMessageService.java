package com.xwder.message.module.wechat.service.intf;

import com.xwder.cloud.commmon.api.CommonResult;

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
    CommonResult sendStrMessage(String msg);

    /**
     * 发送html消息
     * @param url
     */
    CommonResult sendHtmlMessage(String url);
}

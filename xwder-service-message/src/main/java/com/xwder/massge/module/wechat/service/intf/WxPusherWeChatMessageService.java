package com.xwder.massge.module.wechat.service.intf;

import com.xwder.framework.utils.message.Result;

/**
 * wxpusher 微信个人消息推送接口
 *
 * @author xwder
 * @date 2019-11-12 22:46:43
 */
public interface WxPusherWeChatMessageService {

    /**
     * wxpusher 发送字符串消息
     *
     * @param uid
     * @param msg
     * @return
     */
    Result sendStrMessage(String uid, String msg);


    /**
     * wxpusher 发送html消息
     * @param uid
     * @param content
     * @return
     */
    Result sendHtmlMessage(String uid,String content) ;


    /**
     * wxpusher 发送MarkDown消息
     * @param uid
     * @param content
     * @return
     */
    Result sendMarkDownMessage(String uid,String content) ;


}

package com.xwder.message.module.wechat.service.intf;

import com.xwder.cloud.commmon.api.CommonResult;

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
    CommonResult sendStrMessage(String uid, String msg);


    /**
     * wxpusher 发送html消息
     * @param uid
     * @param content
     * @return
     */
    CommonResult sendHtmlMessage(String uid,String content) ;


    /**
     * wxpusher 发送MarkDown消息
     * @param uid
     * @param content
     * @return
     */
    CommonResult sendMarkDownMessage(String uid, String content) ;


}

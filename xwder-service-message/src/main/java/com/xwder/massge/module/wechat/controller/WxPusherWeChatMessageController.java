package com.xwder.massge.module.wechat.controller;

import com.xwder.framework.utils.message.Result;
import com.xwder.massge.module.wechat.service.intf.WxPusherWeChatMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * wxpusher 发送微信消息接口
 *
 * @Author: xwder
 * @Date: 2019/11/13 00:07
 * @Description:
 */
@RestController
@RequestMapping("/wechat/wxpusher")
public class WxPusherWeChatMessageController {

    @Autowired
    private WxPusherWeChatMessageService wxPusherWeChatMessageService;

    /**
     * wxpusher 发送字符串消息
     *
     * @param uid
     * @param msg
     * @return
     */
    @RequestMapping(value = "/sendStrMsg", method = {RequestMethod.POST, RequestMethod.GET})
    Result sendWxPusherWeChatStrMessage(String uid, String msg) {
        return wxPusherWeChatMessageService.sendStrMessage(uid, msg);
    }


    /**
     * wxpusher 发送html消息
     *
     * @param uid
     * @param content
     * @return
     */
    @RequestMapping(value = "/sendHtmlMessage", method = {RequestMethod.POST, RequestMethod.GET})
    Result sendWxPusherWeChatHtmlMessage(String uid, String content) {
        return wxPusherWeChatMessageService.sendHtmlMessage(uid, content);
    }


    /**
     * wxpusher 发送MarkDown消息
     *
     * @param uid
     * @param content
     * @return
     */
    @RequestMapping(value = "/sendMarkDownMessage", method = {RequestMethod.POST, RequestMethod.GET})
    Result sendWxPusherWeChatMarkDownMessage(String uid, String content) {
        return wxPusherWeChatMessageService.sendMarkDownMessage(uid, content);
    }

}

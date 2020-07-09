package com.xwder.massge.module.wechat.controller;

import com.xwder.cloud.commmon.api.CommonResult;
import com.xwder.massge.module.wechat.service.intf.FtqqWeChatMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Server酱发送微信消息接口
 *
 * @Author: xwder
 * @Date: 2019/11/13 00:02
 * @Description:
 */
@RestController
@RequestMapping("/wechat/ftqq")
public class FtqqWeChatMessageController {

    @Autowired
    private FtqqWeChatMessageService ftqqWeChatMessageService;

    /**
     * 此接口只能给我自己微信发送消息
     * 微信消息内容
     *
     * @param msg 消息内容
     * @return
     */
    @RequestMapping(value = "/sendStrMsg", method = {RequestMethod.POST, RequestMethod.GET})
    CommonResult sendFtqqWeChatStrMessage(String msg) {
        return ftqqWeChatMessageService.sendStrMessage(msg);
    }

}

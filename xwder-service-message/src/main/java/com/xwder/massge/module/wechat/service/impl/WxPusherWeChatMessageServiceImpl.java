package com.xwder.massge.module.wechat.service.impl;

import com.xwder.framework.utils.message.ResultUtil;
import com.xwder.massge.module.wechat.common.FtqqResult;
import com.xwder.massge.module.wechat.common.WxPusherResult;
import com.zjiecode.wxpusher.client.WxPusher;
import com.zjiecode.wxpusher.client.bean.Message;

import com.xwder.framework.utils.message.Result;
import com.xwder.massge.module.wechat.config.WeChatMessageConfig;
import com.xwder.massge.module.wechat.service.intf.WxPusherWeChatMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * wxpusher 微信个人消息推送接口
 *
 * @Author: xwder
 * @Date: 2019/11/12 23:14
 * @Description:
 */
@Slf4j
@Service
public class WxPusherWeChatMessageServiceImpl implements WxPusherWeChatMessageService {

    @Autowired
    private WeChatMessageConfig weChatMessageConfig;

    @Override
    public Result sendStrMessage(String uid, String msg) {
        Message message = new Message();
        message.setContentType(Message.CONTENT_TYPE_TEXT);
        message.setUid(uid);
        message.setAppToken(weChatMessageConfig.getWxPusherAppToken());
        message.setContent(msg);
        com.zjiecode.wxpusher.client.bean.Result result = WxPusher.send(message);
        if (WxPusherResult.SUCCESS.getCode().equals(result.getCode())) {
            log.info("WxPusher微信字符串消息发送成功,消息内容=>{}", msg);
            return ResultUtil.success();
        }
        log.error("WxPusher微信字符串消息发送失败,消息内容{}, 错误提示：{}", msg, FtqqResult.getFtqqResult(result.getCode()).getMsg());
        return ResultUtil.error("WxPusher微信字符串消息发送失败," + WxPusherResult.getWxPusherResult(result.getCode()).getMsg());
    }

    @Override
    public Result sendHtmlMessage(String uid, String content) {
        Message message = new Message();
        message.setContentType(Message.CONTENT_TYPE_HTML);
        message.setUid(uid);
        message.setAppToken(weChatMessageConfig.getWxPusherAppToken());
        message.setContent(content);
        com.zjiecode.wxpusher.client.bean.Result result = WxPusher.send(message);
        if (WxPusherResult.SUCCESS.getCode().equals(result.getCode())) {
            log.info("WxPusher微信Html消息发送成功,消息内容消息内容=>{}", content);
            return ResultUtil.success();
        }
        log.error("WxPusher微信Html消息发送失败,消息内容=>{},错误提示{}", content, WxPusherResult.getWxPusherResult(result.getCode()).getMsg());
        return ResultUtil.error("WxPusher微信Html消息发送失败," + WxPusherResult.getWxPusherResult(result.getCode()).getMsg());
    }

    @Override
    public Result sendMarkDownMessage(String uid, String content) {
        Message message = new Message();
        message.setContentType(Message.CONTENT_TYPE_MD);
        message.setUid(uid);
        message.setAppToken(weChatMessageConfig.getWxPusherAppToken());
        message.setContent(content);
        com.zjiecode.wxpusher.client.bean.Result result = WxPusher.send(message);
        if (WxPusherResult.SUCCESS.getCode().equals(result.getCode())) {
            log.info("WxPusher微信Html消息发送成功,消息内容=>{}", content);
            return ResultUtil.success();
        }
        log.error("WxPusher微信Html消息发送失败,消息内容=>{},错误提示{}", content, WxPusherResult.getWxPusherResult(result.getCode()).getMsg());
        return ResultUtil.error("WxPusher微信字符串消息发送失败," + WxPusherResult.getWxPusherResult(result.getCode()).getMsg());
    }
}

package com.xwder.message.module.wechat.service.impl;

import com.xwder.cloud.commmon.api.CommonResult;
import com.xwder.message.module.wechat.common.FtqqResult;
import com.xwder.message.module.wechat.common.WxPusherResult;
import com.xwder.message.module.wechat.config.WeChatMessageConfig;
import com.xwder.message.module.wechat.service.intf.WxPusherWeChatMessageService;
import com.zjiecode.wxpusher.client.WxPusher;
import com.zjiecode.wxpusher.client.bean.Message;
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
    public CommonResult sendStrMessage(String uid, String msg) {
        Message message = new Message();
        message.setContentType(Message.CONTENT_TYPE_TEXT);
        message.setUid(uid);
        message.setAppToken(weChatMessageConfig.getWxPusherAppToken());
        message.setContent(msg);
        com.zjiecode.wxpusher.client.bean.Result result = WxPusher.send(message);
        if (WxPusherResult.SUCCESS.getCode().equals(result.getCode())) {
            log.info("WxPusher微信字符串消息发送成功,消息内容=>{}", msg);
            return CommonResult.success();
        }
        log.error("WxPusher微信字符串消息发送失败,消息内容{}, 错误提示：{}", msg, FtqqResult.getFtqqResult(result.getCode()).getMsg());
        return CommonResult.failed("WxPusher微信字符串消息发送失败," + WxPusherResult.getWxPusherResult(result.getCode()).getMsg());
    }

    @Override
    public CommonResult sendHtmlMessage(String uid, String content) {
        Message message = new Message();
        message.setContentType(Message.CONTENT_TYPE_HTML);
        message.setUid(uid);
        message.setAppToken(weChatMessageConfig.getWxPusherAppToken());
        message.setContent(content);
        com.zjiecode.wxpusher.client.bean.Result result = WxPusher.send(message);
        if (WxPusherResult.SUCCESS.getCode().equals(result.getCode())) {
            log.info("WxPusher微信Html消息发送成功,消息内容消息内容=>{}", content);
            return CommonResult.success();
        }
        log.error("WxPusher微信Html消息发送失败,消息内容=>{},错误提示{}", content, WxPusherResult.getWxPusherResult(result.getCode()).getMsg());
        return CommonResult.failed("WxPusher微信Html消息发送失败," + WxPusherResult.getWxPusherResult(result.getCode()).getMsg());
    }

    @Override
    public CommonResult sendMarkDownMessage(String uid, String content) {
        Message message = new Message();
        message.setContentType(Message.CONTENT_TYPE_MD);
        message.setUid(uid);
        message.setAppToken(weChatMessageConfig.getWxPusherAppToken());
        message.setContent(content);
        com.zjiecode.wxpusher.client.bean.Result result = WxPusher.send(message);
        if (WxPusherResult.SUCCESS.getCode().equals(result.getCode())) {
            log.info("WxPusher微信Html消息发送成功,消息内容=>{}", content);
            return CommonResult.success();
        }
        log.error("WxPusher微信Html消息发送失败,消息内容=>{},错误提示{}", content, WxPusherResult.getWxPusherResult(result.getCode()).getMsg());
        return CommonResult.failed("WxPusher微信字符串消息发送失败," + WxPusherResult.getWxPusherResult(result.getCode()).getMsg());
    }
}

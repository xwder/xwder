package com.xwder.massge.module.wechat.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.xwder.framework.utils.message.Result;
import com.xwder.framework.utils.message.ResultUtil;
import com.xwder.framework.utils.request.HttpClientUtil;
import com.xwder.massge.module.wechat.common.FtqqResult;
import com.xwder.massge.module.wechat.config.WeChatMessageConfig;
import com.xwder.massge.module.wechat.service.intf.FtqqWeChatMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Server酱 微信个人消息推送接口
 *
 * @author wande
 * @version 1.0
 * @date 2019/11/12
 */
@Service
@Slf4j
public class FtqqWeChatMessageServiceImpl implements FtqqWeChatMessageService {

    @Autowired
    WeChatMessageConfig weChatMessageConfig;

    @Override
    public Result sendStrMessage(String msg) {
        String result = HttpClientUtil.doGet(weChatMessageConfig.getFtqqUrl() + msg);
        JSONObject jsonObject = JSONObject.parseObject(result);
        int errno = (int) jsonObject.get("errno");
        if (FtqqResult.SUCCESS.getCode() == errno) {
            log.info("Server酱 微信消息发送成功,消息内容{}", msg);
            return ResultUtil.success();
        }
        log.error("Server酱微信消息发送失败,消息内容=>{},错误提示 {}", msg, FtqqResult.getFtqqResult(errno));
        return ResultUtil.error("Server酱微信消息发送失败");
    }

    @Override
    public Result sendHtmlMessage(String url) {
        return ResultUtil.error("暂时无法发送html消息");
    }
}

package com.xwder.manage.modules.message.service.intl;

/**
 * @Author: xwder
 * @Date: 2020/1/14 23:34
 * @Description:
 */
public interface WeChatMessageService {

    /**
     * 发送 wxpusher 微信通知
     *
     * @param uid
     * @param content
     * @return
     */
    boolean sendStrMessage(String uid, String content);

}

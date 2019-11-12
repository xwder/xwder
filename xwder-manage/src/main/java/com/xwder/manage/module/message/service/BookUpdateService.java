package com.xwder.manage.module.message.service;

import com.xwder.framework.utils.message.Result;

import java.util.List;
import java.util.Map;

/**
 * @Author: xwder
 * @Date: 2019/7/14 23:36
 * @Description:
 */
public interface BookUpdateService {

    /**
     * 获取小说更新情况
     *
     * @param author
     * @param bookName
     * @return
     */
    List<Map> getBookUpdateInfo(String author, String bookName);

    /**
     * 发送小说更新mail
     *
     * @param list
     * @param mailMap
     * @return
     */
    Result sendBookUpdateMailMessage(List<Map> list, Map mailMap);

    /**
     * 发送小说更新sms提醒
     * @param phone
     * @param content
     * @return
     */
    Result sendBookUpdateSMSMessage(String phone, String content);

    /**
     * 给只用邮件发送指定小说更新信息
     *
     * @param author
     * @param bookName
     * @param to
     * @param subject
     * @return
     */
    Result sendBookUpdateMessageWithMailAndSMS(String author, String bookName, String to, String subject);

    /**
     * wxpusher 发送字符串消息
     *
     * @param uid
     * @param msg
     * @return
     */
    Result sendWxPusherWeChatStrMessage(String uid, String msg);
}

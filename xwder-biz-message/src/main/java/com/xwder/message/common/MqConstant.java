package com.xwder.message.common;


/**
 * 消息队列常量
 *
 * @author wande
 * @version 1.0
 * @date 2020/07/10
 */
public class MqConstant {

    /**
     * sms 模块交换机
     */
    public static final String XWDER_EXCHANGE_SMS = "xwder.exchange.sms";

    /**
     * email 模块交换机
     */
    public static final String XWDER_EXCHANGE_EMAIL = "xwder.exchange.email";

    /**
     * alertover 模块交换机
     */
    public static final String XWDER_EXCHANGE_ALERTOVER = "xwder.exchange.alertover";

    /**
     * wechat 模块交换机
     */
    public static final String XWDER_EXCHANGE_WECHAT = "xwder.exchange.alertover";


    /**
     * 书籍模块短信消息队列
     */
    public static final String XWDER_QUEUE_SMS = "xwder.queue.sms";

    /**
     * 书籍模块邮件消息队列
     */
    public static final String XWDER_QUEUE_EMAIL = "xwder.queue.email";

    /**
     * 书籍模块邮件消息队列
     */
    public static final String XWDER_QUEUE_ALERTOVER = "xwder.queue.alertover";

    /**
     * 书籍模块微信通知消息队列
     */
    public static final String XWDER_QUEUE_WECHAT = "xwder.queue.wechat";




    /**
     * 书籍模块短信消息routingkey
     */
    public static final String XWDER_ROUTINGKEY_SMS_CHAPTERUPDATE = "xwder-sms-chapterUpdate";

    /**
     * 书籍模块邮件消息routingkey
     */
    public static final String XWDER_ROUTINGKEY_EMAIL_CHAPTERUPDATE = "xwder-email-chapterUpdate";

    /**
     * 书籍模块邮件消息routingkey
     */
    public static final String XWDER_ROUTINGKEY_ALERTOVER_CHAPTERUPDATE = "xwder-alertover-chapterUpdate";

    /**
     * 书籍模块微信通知消息routingkey
     */
    public static final String XWDER_ROUTINGKEY_WECHAT_CHAPTERUPDATE = "xwder-wechat-chapterUpdate";

    /**
     * 邮箱验证消息routingkey
     */
    public static final String XWDER_ROUTINGKEY_EMAIL_VERIFYEMAIL = "xwder-email-verifyEmail";
}

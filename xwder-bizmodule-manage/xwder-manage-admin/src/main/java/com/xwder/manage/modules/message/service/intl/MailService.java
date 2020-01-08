package com.xwder.manage.modules.message.service.intl;

/**
 * @author xwder
 * @Description:Service接口，定义邮件发送的方法
 * @date: 2019年7月13日19:22:45
 */
public interface MailService {
    /**
     * 发送简单邮件
     *
     * @param to
     * @param subject
     * @param content
     */
	boolean sendSimpleMail(String to, String subject, String content);

    /**
     * 发送html邮件
     *
     * @param to
     * @param subject
     * @param content
     */
	boolean sendHtmlMail(String to, String subject, String content);

}
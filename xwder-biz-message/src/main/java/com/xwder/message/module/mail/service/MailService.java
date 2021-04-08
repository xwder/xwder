package com.xwder.message.module.mail.service;
 
/**
 * @Description:Service接口，定义邮件发送的方法
 * @author xwder
 * @date: 2019年7月13日19:22:45
 */
public interface MailService {
	/**
	 *
	 * @param to
	 * @param subject
	 * @param content
	 */
	 void sendSimpleMail(String to, String subject, String content);

	/**
	 *
	 * @param to
	 * @param subject
	 * @param content
	 */
	 void sendHtmlMail(String to, String subject, String content);

	/**
	 *
	 * @param to
	 * @param subject
	 * @param content
	 * @param filePath
	 */
	 void sendAttachmentsMail(String to, String subject, String content, String filePath);
}
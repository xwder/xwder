package com.xwder.message.module.mail.service.impl;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.xwder.message.module.mail.service.MailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;


/**
 * @author xwder
 * @Description:邮件发送的实现类
 */
@Slf4j
@Service
public class MailServiceImpl implements MailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${mail.fromMail.addr}")
    private String from;

    /**
     * @return
     * @Description:发送简单邮件(收件人，主题，内容都暂时写死)
     */
    @HystrixCommand(fallbackMethod = "sendSimpleMailFallBack")
    @Override
    public void sendSimpleMail(String to, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);
        try {
            mailSender.send(message);
            log.info("简单邮件发送成功！");
        } catch (Exception e) {
            log.error("发送简单邮件时发生异常！ {}", e);
        }
    }

    /**
     * 服务不可用 降级回调方法
     *
     * @return
     */
    public void sendSimpleMailFallBack(String to, String subject, String content) {
    }

    /**
     * @return
     * @Description:发送Html邮件(收件人，主题，内容都暂时写死)
     */
    @HystrixCommand(fallbackMethod = "sendHtmlMailFallback")
    @Override
    public void sendHtmlMail(String to, String subject, String content) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            //true表示需要创建一个multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);
            mailSender.send(message);
            log.info("html邮件发送成功");
        } catch (MessagingException e) {
            log.error("发送html邮件时发生异常！ {}", e);
        }
    }

    /**
     * 服务不可用 降级回调方法
     *
     * @return
     */
    public void sendHtmlMailFallback(String to, String subject, String content) {
    }

    /**
     * 发送带附件的邮件
     *
     * @param to
     * @param subject
     * @param content
     * @param filePath
     */
    @HystrixCommand(fallbackMethod = "sendAttachmentsMailFallback")
    @Override
    public void sendAttachmentsMail(String to, String subject, String content, String filePath) {
        MimeMessage message = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);

            FileSystemResource file = new FileSystemResource(new File(filePath));
            String fileName = filePath.substring(filePath.lastIndexOf(File.separator));
            helper.addAttachment(fileName, file);
            //helper.addAttachment("test"+fileName, file);

            mailSender.send(message);
            log.info("带附件的邮件已经发送。");
        } catch (MessagingException e) {
            log.error("发送带附件的邮件时发生异常！ {}", e);
        }
    }

    /**
     * 服务不可用 降级回调方法
     *
     * @return
     */
    public void sendAttachmentsMailFallback(String to, String subject, String content, String filePath) {
    }
}
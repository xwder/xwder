package com.xwder.massge.module.mail.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.activation.DataSource;
import javax.mail.internet.MimeMessage;
import java.util.List;

public class EmailUtils {
    // 引入javaMailSender 
    private JavaMailSender mailSender;

    public EmailUtils(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }
    // 发送简单邮件 
    public void sendSimpleMail(String from, String to, String cc, String subject, String text) {
        if(StringUtils.isBlank(to)){
            return;
        }
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to.split(","));
        if (StringUtils.isNotBlank(cc)) {
            message.setCc(cc.split(","));
        }
        message.setSubject(subject);
        message.setText(text);

        mailSender.send(message);
    }
    // 发送带多个附件的邮件
    public void sendAttachmentMail(String from, String to, String cc, String subject, String text, List<Attachment> attachments) throws Exception {
        if(StringUtils.isBlank(to)){
            return;
        }
        MimeMessage mimeMessage = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setFrom(from);
        helper.setTo(to.split(","));
        if (StringUtils.isNotBlank(cc)) {
            helper.setCc(cc.split(","));
        }
        helper.setSubject(subject);
        helper.setText(text);
        for (Attachment attachment : attachments) {
            String attachmentName = attachment.attachmentName;
            DataSource attachmentFile = attachment.attachmentFile;
            helper.addAttachment(attachmentName, attachmentFile);
        }
        mailSender.send(mimeMessage);
    }
   // 发送带单个附件的邮件
    public void sendAttachmentMail(String from, String to, String cc, String subject, String text, Attachment attachment) throws Exception {
        if (StringUtils.isBlank(to)) {
            return;
        }
        MimeMessage mimeMessage = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setFrom(from);
        helper.setTo(to.split(","));
        if (StringUtils.isNotBlank(cc)) {
            helper.setCc(cc.split(","));
        }

        helper.setSubject(subject);
        helper.setText(text);
        String attachmentName = attachment.attachmentName;
        DataSource attachmentFile = attachment.attachmentFile;
        helper.addAttachment(attachmentName, attachmentFile);

        mailSender.send(mimeMessage);
    }

    public static class Attachment {
        private String attachmentName;
        private DataSource attachmentFile;

        public Attachment(String attachmentName, DataSource attachmentFile) {
            this.attachmentName = attachmentName;
            this.attachmentFile = attachmentFile;
        }
    }
}

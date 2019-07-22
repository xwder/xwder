package com.xwder.massge.mail.controller;

import com.xwder.framework.utils.message.Result;
import com.xwder.framework.utils.message.ResultUtil;
import com.xwder.massge.mail.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.TemplateEngine;

/**
 * @author xwder
 * @Description:发送邮件的Controller
 * @date:2019年7月18日00:06:41
 */
@RestController
@RequestMapping("/mail")
public class MailController {
    @Autowired
    private MailService mailService;

    @Autowired
    private TemplateEngine templateEngine;

    /**
     * @return
     */
    @RequestMapping("/sendSimpleMail")
    public Result sendSimpleMail(String to, String subject, String content) {
//		String to = "xwder@xwder.com";
//		String subject = "test simple mail";
//		String content = "hello, this is simple mail";
        mailService.sendHtmlMail(to, subject, content);
        return ResultUtil.success();
    }

    /**
     * @return
     * @Description:发送Html格式的邮件(收件人，主题，内容都暂时写死)
     */
    @RequestMapping("/sendHtmlMail")
    public String sendHtmlMail(String to, String subject, String content) {
        mailService.sendHtmlMail(to, subject, content);
        return "success";
    }


}
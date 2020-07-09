package com.xwder.massge.module.mail.controller;

import com.xwder.cloud.commmon.api.CommonResult;
import com.xwder.massge.module.mail.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
     * 发送简单邮件
     *
     * @param to      目标邮箱账号
     * @param subject 主题
     * @param content 内容
     * @return
     */
    @RequestMapping(value = "/sendSimpleMail",method = RequestMethod.POST)
    public CommonResult sendSimpleMail(String to, String subject, String content) {
        mailService.sendHtmlMail(to, subject, content);
        return CommonResult.success();
    }


    /**
     * 发送Html格式的邮件(收件人，主题，内容都暂时写死)
     *
     * @param to      目标邮箱账号
     * @param subject 主题
     * @param content 内容
     * @return
     */
    @RequestMapping("/sendHtmlMail")
    public String sendHtmlMail(String to, String subject, String content) {
        mailService.sendHtmlMail(to, subject, content);
        return "success";
    }


}
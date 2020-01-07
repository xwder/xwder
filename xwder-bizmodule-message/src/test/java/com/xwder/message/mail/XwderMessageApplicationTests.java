package com.xwder.message.mail;

import com.xwder.massge.XwderMessageApplication;
import com.xwder.massge.module.mail.service.MailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = XwderMessageApplication.class)
public class XwderMessageApplicationTests {

    @Autowired
    private MailService mailService;

    @Autowired
    private TemplateEngine templateEngine;

	@Test
	public void contextLoads() {
	}

    /**
     * @Description:发送带有附件的邮件
     * 访问地址：http://localhost:8080/mail/sendAttachmentsMail
     */
    @Test
    public void sendAttachmentsMail() {
        String filePath="C:\\Users\\xwder\\Desktop\\李润平个人简历.docx";
        mailService.sendAttachmentsMail("xwder@xwder.com", "主题：带附件的邮件", "有附件，请查收！", filePath);
    }


    /**
     * @Description:发送模板邮件
     * 访问地址：http://localhost:8080/mail/sendTemplateMail
     */
    @Test
    public void sendTemplateMail() {
        //创建邮件正文
        Context context = new Context();
        context.setVariable("user", "Joe");
        context.setVariable("web", "@xwder.com");
        context.setVariable("company", "xwder.com");
        context.setVariable("product","xwder-service-message");
        String emailContent = templateEngine.process("emailTemplate", context);
        mailService.sendHtmlMail("1123511540@qq.com","主题：这是模板邮件",emailContent);
    }

}

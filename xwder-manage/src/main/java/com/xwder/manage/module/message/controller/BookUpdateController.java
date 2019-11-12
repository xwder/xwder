package com.xwder.manage.module.message.controller;

import com.xwder.api.message.mail.MessageServiceApi;
import com.xwder.framework.utils.message.Result;
import com.xwder.manage.module.message.service.BookUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: xwder
 * @Date: 2019/7/16 01:23
 * @Description:
 */
@RequestMapping("/msg")
@Controller
public class BookUpdateController {


    @Autowired
    private BookUpdateService bookUpdateService;
    @Autowired
    private MessageServiceApi messageServiceApi;

    @RequestMapping(value = "/update/sendBookUpdateMail", method = RequestMethod.POST)
    @ResponseBody
    public Result sendBooUpdateMessageWithMailAndBooInfo(String author, String bookName, String to, String subject) {
        Result result = bookUpdateService.sendBookUpdateMessageWithMailAndSMS(author, bookName, to, subject);
        return result;
    }

    @RequestMapping(value = "/sendWxPusherWeChatStrMessage", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Result sendWxPusherWeChatStrMessage(String uid, String msg) {

        Result result = messageServiceApi.sendWxPusherWeChatStrMessage(uid, msg);
        return result;
    }

}

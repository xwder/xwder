package com.xwder.api.message.mail;

import com.xwder.api.message.mail.fallback.MessageServiceFallbackFactory;
import com.xwder.framework.utils.message.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * https://blog.csdn.net/neosmith/article/details/82349449
 *
 * @Author: xwder
 * @Date: 2019/7/14 23:49
 * @Description:
 */

@FeignClient(name = "XWDER-SERVICE-MESSAGE", fallbackFactory = MessageServiceFallbackFactory.class)
public interface MessageServiceApi {

    /**
     * 发送更新消息
     *
     * @param to
     * @param subject
     * @param content
     * @return
     */
    @RequestMapping(value = "/mail/sendSimpleMail")
    public Result sendSimpleMail(@RequestParam("to") final String to, @RequestParam("subject") final String subject, @RequestParam("content") final String content);


    /**
     * 发送短信
     *
     * @param phone   手机号
     * @param content 短信内容
     * @return
     */
    @RequestMapping("/sendSmsMessage")
    public Result sendQcloudSMS(@RequestParam("phone") final String phone, @RequestParam("content") final String content);
}

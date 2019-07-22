package com.xwder.api.message;

import com.xwder.api.message.fallback.MessageServiceFallbackFactory;
import com.xwder.framework.utils.message.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: xwder
 * @Date: 2019/7/14 23:49
 * @Description:
 */

@FeignClient(value = "XWDER-PROVIDER-MESSAGE", fallbackFactory = MessageServiceFallbackFactory.class)
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

}

package com.xwder.api.message.fallback;

import com.xwder.api.message.MessageServiceApi;
import com.xwder.framework.utils.message.Result;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @Author: xwder
 * @Date: 2019/7/17 23:41
 * @Description:
 */
@Component
public class MessageServiceFallbackFactory implements FallbackFactory<MessageServiceApi> {
    @Override
    public MessageServiceApi create(Throwable throwable) {
        return new MessageServiceApi() {

            @Override
            public Result sendSimpleMail(String to, String subject, String content) {
                System.out.println("调用 sendSimpleMail 失败");
                return null;
            }
        };
    }
}

package com.xwder.massge.module.mq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wande
 * @version 1.0
 * @date 2020/01/13
 */

@Configuration
public class RabbitConfig {


    /**
     * 书籍模块交换机
     */
    public final static String XWDER_EXCHANGE_BOOK = "xwder.exchange.book";

    /**
     * 书籍模块短信消息队列                                   福 代码写到这里的时候去集五福去了
     */
    public final static String XWDER_SMS_QUEUE_BOOK_UPDATE = "xwder.queue.sms.chapterUpdate";

    /**
     * 书籍模块邮件消息队列
     */
    public final static String XWDER_EMAIL_QUEUE_BOOK_UPDATE = "xwder.queue.email.chapterUpdate";

    /**
     * 书籍模块邮件消息队列
     */
    public final static String XWDER_ALERTOVER_QUEUE_BOOK_UPDATE = "xwder.queue.alertover.chapterUpdate";

    /**
     * 书籍模块微信通知消息队列
     */
    public final static String XWDER_WECHAT_QUEUE_BOOK_UPDATE = "xwder.queue.wechat.chapterUpdate";

    @Bean
    public Queue smsBookUpdateQueue() {
        return new Queue(RabbitConfig.XWDER_SMS_QUEUE_BOOK_UPDATE);
    }

    @Bean
    public Queue emailBookUpdateQueue() {
        return new Queue(RabbitConfig.XWDER_EMAIL_QUEUE_BOOK_UPDATE);
    }

    @Bean
    public Queue alertOverBookUpdateQueue() {
        return new Queue(RabbitConfig.XWDER_ALERTOVER_QUEUE_BOOK_UPDATE);
    }

    @Bean
    public Queue weChatBookUpdateQueue() {
        return new Queue(RabbitConfig.XWDER_WECHAT_QUEUE_BOOK_UPDATE);
    }

    @Bean
    TopicExchange xwderBookExchange() {
        return new TopicExchange(RabbitConfig.XWDER_EXCHANGE_BOOK);
    }

    /**
     * 将 Queue 和 topicExchange 绑定,而且绑定的键值为 sms.bookUpdate
     * 这样只要是消息携带的路由键是 sms.bookUpdate ,才会分发到该队列
     *
     * @return
     */
    @Bean
    Binding bindingXwderBookSMSExchangeMessage() {
        return BindingBuilder.bind(smsBookUpdateQueue()).to(xwderBookExchange()).with(XWDER_SMS_QUEUE_BOOK_UPDATE);
    }

    @Bean
    Binding bindingXwderBookEmailExchangeMessage() {
        return BindingBuilder.bind(emailBookUpdateQueue()).to(xwderBookExchange()).with(XWDER_EMAIL_QUEUE_BOOK_UPDATE);
    }

    @Bean
    Binding bindingXwderBookAlertOverExchangeMessage() {
        return BindingBuilder.bind(alertOverBookUpdateQueue()).to(xwderBookExchange()).with(XWDER_ALERTOVER_QUEUE_BOOK_UPDATE);
    }

    @Bean
    Binding bindingXwderBookWeChatExchangeMessage() {
        return BindingBuilder.bind(weChatBookUpdateQueue()).to(xwderBookExchange()).with(XWDER_WECHAT_QUEUE_BOOK_UPDATE);
    }
}

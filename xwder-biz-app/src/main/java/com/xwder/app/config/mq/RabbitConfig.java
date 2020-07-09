package com.xwder.app.config.mq;

import lombok.Data;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wande
 * @version 1.0
 * @date 2020/01/13
 */
@Data
@Configuration
public class RabbitConfig {

    /**
     * 书籍模块交换机
     */
    @Value("${xwder.exchange.book}")
    private String xwderExchageBook = "xwder.exchange.book";

    /**
     * 书籍模块短信消息队列                                   福 代码写到这里的时候去集五福去了
     */
    @Value("${xwder.queue.sms.chapterUpdate}")
    private String xwderQueueSmsChapterUpdate = "xwder.queue.sms.chapterUpdate";

    /**
     * 书籍模块邮件消息队列
     */
    @Value("${xwder.queue.email.chapterUpdate}")
    private String xwderQueueEmailChapterUpdate = "xwder.queue.email.chapterUpdate";

    /**
     * 书籍模块邮件消息队列
     */
    @Value("${xwder.queue.alertover.chapterUpdate}")
    private String xwderQueueAlertOverChapterUpdate = "xwder.queue.alertover.chapterUpdate";

    /**
     * 书籍模块微信通知消息队列
     */
    @Value("${xwder.queue.wechat.chapterUpdate}")
    private String xwderQueueWeChatChapterUpdate = "xwder.queue.wechat.chapterUpdate";

    @Bean
    public Queue smsBookUpdateQueue() {
        return new Queue(xwderQueueSmsChapterUpdate);
    }

    @Bean
    public Queue emailBookUpdateQueue() {
        return new Queue(xwderQueueEmailChapterUpdate);
    }

    @Bean
    public Queue alertOverBookUpdateQueue() {
        return new Queue(xwderQueueAlertOverChapterUpdate);
    }

    @Bean
    public Queue weChatBookUpdateQueue() {
        return new Queue(xwderQueueWeChatChapterUpdate);
    }

    @Bean
    TopicExchange xwderBookExchange() {
        return new TopicExchange(xwderExchageBook);
    }

    /**
     * 将 Queue 和 topicExchange 绑定,而且绑定的键值为 sms.bookUpdate
     * 这样只要是消息携带的路由键是 sms.bookUpdate ,才会分发到该队列
     *
     * @return
     */
    @Bean
    Binding bindingXwderBookSMSExchangeMessage() {
        return BindingBuilder.bind(smsBookUpdateQueue()).to(xwderBookExchange()).with(xwderQueueSmsChapterUpdate);
    }

    @Bean
    Binding bindingXwderBookEmailExchangeMessage() {
        return BindingBuilder.bind(emailBookUpdateQueue()).to(xwderBookExchange()).with(xwderQueueEmailChapterUpdate);
    }

    @Bean
    Binding bindingXwderBookAlertOverExchangeMessage() {
        return BindingBuilder.bind(alertOverBookUpdateQueue()).to(xwderBookExchange()).with(xwderQueueAlertOverChapterUpdate);
    }

    @Bean
    Binding bindingXwderBookWeChatExchangeMessage() {
        return BindingBuilder.bind(weChatBookUpdateQueue()).to(xwderBookExchange()).with(xwderQueueWeChatChapterUpdate);
    }

}

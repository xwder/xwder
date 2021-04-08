package com.xwder.message.config.mq;

import lombok.Data;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * @author wande
 * @version 1.0
 * @date 2020/01/13
 */
@Data
@Configuration
public class RabbitConfig {

    @Value("${spring.rabbitmq.host}")
    private String host;

    @Value("${spring.rabbitmq.port}")
    private int port;

    @Value("${spring.rabbitmq.username}")
    private String username;

    @Value("${spring.rabbitmq.password}")
    private String password;

    @Value("${spring.rabbitmq.virtual-host}")
    private String virtualHost;

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(host, port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost(virtualHost);
        //设置发送消息失败重试
        connectionFactory.setPublisherConfirms(true);
        //解决多线程发送消息
        connectionFactory.setChannelCacheSize(100);

        return connectionFactory;
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate template = new RabbitTemplate(connectionFactory());
        //设置发送消息失败重试
        template.setMandatory(true);
        return template;

    }

    //配置使用json转递数据
    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    /**
     * sms 模块交换机
     */
    @Value("${mq.exchange.sms}")
    private String xwderExchageSms;

    /**
     * email 模块交换机
     */
    @Value("${mq.exchange.email}")
    private String xwderExchageEmail;

    /**
     * alertover 模块交换机
     */
    @Value("${mq.exchange.alertover}")
    private String xwderExchageAlertOver;

    /**
     * wechat 模块交换机
     */
    @Value("${mq.exchange.wechat}")
    private String xwderExchageWeChat;


    /**
     * 书籍模块短信消息队列
     */
    @Value("${mq.queue.sms}")
    private String xwderQueueSms;

    /**
     * 书籍模块邮件消息队列
     */
    @Value("${mq.queue.email}")
    private String xwderQueueEmail;

    /**
     * 书籍模块邮件消息队列
     */
    @Value("${mq.queue.alertover}")
    private String xwderQueueAlertOver;

    /**
     * 书籍模块微信通知消息队列
     */
    @Value("${mq.queue.wechat}")
    private String xwderQueueWeChat;


    /**
     * 书籍模块短信消息routingkey
     */
    @Value("${mq.routingkey.sms.chapterUpdate}")
    private String xwderSmsChapterUpdateRoutingkey;

    /**
     * 书籍模块邮件消息routingkey
     */
    @Value("${mq.routingkey.email.chapterUpdate}")
    private String xwderEmailChapterUpdateRoutingkey;

    /**
     * 书籍模块邮件消息routingkey
     */
    @Value("${mq.routingkey.alertover.chapterUpdate}")
    private String xwderAlertOverChapterUpdateRoutingkey;

    /**
     * 书籍模块微信通知消息routingkey
     */
    @Value("${mq.routingkey.wechat.chapterUpdate}")
    private String xwderWeChatChapterUpdateRoutingkey;

    /**
     * 邮箱验证消息routingkey
     */
    @Value("${mq.routingkey.email.verifyEmail}")
    private String xwderEmailVerifyEmailRoutingkey;


    /**
     * sms 交换机
     * 针对消费者配置
     * 1. 设置交换机类型
     * 2. 将队列绑定到交换机
     * FanoutExchange: 将消息分发到所有的绑定队列，无 routingkey的概念
     * HeadersExchange: 通过添加属性key - value匹配
     * DirectExchange: 按照routingkey分发到指定队列
     * TopicExchange : 多关键字匹配
     *
     * @return
     */
    @Bean
    TopicExchange xwderSmsExchange() {
        return new TopicExchange(xwderExchageSms);
    }

    /**
     * email 交换机
     */
    @Bean
    TopicExchange xwderEmailExchange() {
        return new TopicExchange(xwderExchageEmail);
    }

    /**
     * alertover 交换机
     */
    @Bean
    TopicExchange xwderAlertOverExchange() {
        return new TopicExchange(xwderExchageAlertOver);
    }

    /**
     * wechat 交换机
     */
    @Bean
    TopicExchange xwderWeChatExchange() {
        return new TopicExchange(xwderExchageWeChat);
    }

    @Bean
    public Queue smsBookUpdateQueue() {
        return new Queue(xwderQueueSms, true);
    }

    @Bean
    public Queue emailBookUpdateQueue() {
        return new Queue(xwderQueueEmail, true);
    }

    @Bean
    public Queue alertOverBookUpdateQueue() {
        return new Queue(xwderQueueAlertOver, true);
    }

    @Bean
    public Queue weChatBookUpdateQueue() {
        return new Queue(xwderQueueWeChat, true);
    }

    /**
     * 将 Queue 和 topicExchange 绑定,而且绑定的键值为 sms.bookUpdate
     * 这样只要是消息携带的路由键是 sms.bookUpdate ,才会分发到该队列
     *
     * @return
     */
    @Bean
    Binding bindingXwderBookSMSExchangeMessage() {
        return BindingBuilder.bind(smsBookUpdateQueue()).to(xwderSmsExchange()).with(xwderSmsChapterUpdateRoutingkey);
    }

    @Bean
    Binding bindingXwderBookEmailExchangeMessage() {
        return BindingBuilder.bind(emailBookUpdateQueue()).to(xwderEmailExchange()).with(xwderEmailChapterUpdateRoutingkey);
    }

    @Bean
    Binding bindingXwderBookAlertOverExchangeMessage() {
        return BindingBuilder.bind(alertOverBookUpdateQueue()).to(xwderAlertOverExchange()).with(xwderAlertOverChapterUpdateRoutingkey);
    }

    @Bean
    Binding bindingXwderBookWeChatExchangeMessage() {
        return BindingBuilder.bind(weChatBookUpdateQueue()).to(xwderWeChatExchange()).with(xwderWeChatChapterUpdateRoutingkey);
    }

    @Bean
    Binding bindingXwderVerifyEmailExchange() {
        return BindingBuilder.bind(emailBookUpdateQueue()).to(xwderEmailExchange()).with(xwderEmailVerifyEmailRoutingkey);
    }

}

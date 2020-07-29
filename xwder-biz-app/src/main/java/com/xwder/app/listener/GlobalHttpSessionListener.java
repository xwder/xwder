package com.xwder.app.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * @author wande
 * @version 1.0
 * @date 2020/07/28
 */
@Slf4j
@Component
@WebListener
public class GlobalHttpSessionListener implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        log.info("创建会话,会话id: {} ", se.getSession().getId());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        log.info("销毁会话,会话id: {} ", se.getSession().getId());
    }

}

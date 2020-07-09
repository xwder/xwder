package com.xwder.manage.modules.message.service.intl;

/**
 * @Author: xwder
 * @Date: 2020/1/8 23:00
 * @Description:
 */
public interface AlertOverService {

    /**
     * @param title
     * @param content
     * @return
     */
    boolean sendStrMessge(String title, String content);
}

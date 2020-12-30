package com.xwder.app.interceptor;

import com.xwder.app.attribute.SysConfigAttribute;
import com.xwder.app.consts.SysConfigConstants;
import com.xwder.app.modules.user.entity.User;
import com.xwder.app.utils.SessionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * novel 拦截器
 *
 * @author wande
 * @version 1.0
 * @date 2020/08/24
 */
@Slf4j
@Component
public class NovelInterceptor implements HandlerInterceptor {

    private String novelUserName = "xwder";

    @Autowired
    private SysConfigAttribute sysConfigAttribute;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        User sessionUser = (User) SessionUtil.getSessionAttribute(SysConfigConstants.SESSION_USER);
        StringBuffer requestURLSB = request.getRequestURL();
        log.info("novel登录拦截器获取到用户访问的地址: {}", requestURLSB.toString());
        if (sessionUser == null) {
            response.sendRedirect("/login.html?redirect=" + requestURLSB);
            return false;
        }
        if (novelUserName.equalsIgnoreCase(sessionUser.getUserName())) {
            return true;
        } else {
            response.sendRedirect("/index.html");
            return false;
        }
    }
}

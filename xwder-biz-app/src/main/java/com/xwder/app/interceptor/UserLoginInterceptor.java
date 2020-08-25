package com.xwder.app.interceptor;

import cn.hutool.core.util.StrUtil;
import com.xwder.app.attribute.SysConfigAttribute;
import com.xwder.app.consts.SysConstant;
import com.xwder.app.modules.user.entity.User;
import com.xwder.app.modules.user.service.intf.UserService;
import com.xwder.app.utils.CookieUtils;
import com.xwder.app.utils.SessionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author xwder
 * @Description: 用户登录拦截
 * @date 2020/7/13 12:24
 */
@Slf4j
@Component
public class UserLoginInterceptor implements HandlerInterceptor {

    @Autowired
    private UserService userService;

    @Autowired
    private SysConfigAttribute sysConfigAttribute;

    /**
     * 请求处理前，也就是访问Controller前
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        User sessionUser = (User) SessionUtil.getSessionAttribute(SysConstant.SESSION_USER);
        StringBuffer requestURLSB = request.getRequestURL();
        log.info("用户登录拦截器获取到用户访问的地址: {}", requestURLSB.toString());
        String userSessionToken = CookieUtils.getCookieValue(request, sysConfigAttribute.getSessionTokenName());
        if (sessionUser == null) {
            if (StrUtil.isNotEmpty(userSessionToken)) {
                // 从redis中获取
                User redisSessionUser = userService.getUserSessionFromRedis(userSessionToken);
                if (redisSessionUser != null) {
                    // session 写入用户信息
                    SessionUtil.setSessionAttribute(SysConstant.SESSION_USER, redisSessionUser);
                }else {
                    response.sendRedirect("/login.html?redirect=" + requestURLSB);
                    return false;
                }
            }else {
                // request.getRequestDispatcher("/login.html").forward(request, response);
                response.sendRedirect("/login.html?redirect=" + requestURLSB);
                return false;
            }
        }

        // 更新redis中session时间 可以考虑使用异步处理
        userService.updateRedisUserSessionExpireTime(userSessionToken,SysConstant.USER_SESSION_REDIS_TIME);
        return true;
    }

    /**
     * /请求已经被处理，但在视图渲染前
     *
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    /**
     * 请求结果结果已经渲染好了，response没有进行返回但也无法修改reponse了（一般用于清理数据）
     *
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }



}

package com.xwder.app.interceptor;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.xwder.app.attribute.SysConfigAttribute;
import com.xwder.app.consts.SysConstant;
import com.xwder.app.modules.blog.service.intf.CategoryService;
import com.xwder.app.modules.user.entity.User;
import com.xwder.app.modules.user.service.intf.UserService;
import com.xwder.app.utils.CookieUtils;
import com.xwder.app.utils.SessionUtil;
import com.xwder.app.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xwder
 * @Description: 全局拦截器 用于处理全局数据
 * @date 2020/7/28 11点14分
 */
@Slf4j
@Component
public class GlobalInterceptor implements HandlerInterceptor {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SysConfigAttribute sysConfigAttribute;

    @Autowired
    private UserService userService;

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
        log.info("全局数据处理拦截器 GoableInterceptor拦截到访问的地址: {}", request.getRequestURL().toString());
        getLoginUser(request,response,handler);
        buildPotalData(request, response, handler);
        refreshRedisSession(request, response, handler);
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


    /**
     * 构建首页数据 放在session中map统一管理
     *
     * @param request
     * @param response
     * @param handler
     */
    private void buildPotalData(HttpServletRequest request, HttpServletResponse response, Object handler) {
        HttpSession session = request.getSession();
        Map potalDataMap = (Map) session.getAttribute("potalDataMap");
        if (CollectionUtil.isNotEmpty(potalDataMap)) {
            if (CollectionUtil.isEmpty((List) potalDataMap.get("categoryMapList"))) {
                // 博客归档分类信息
                List<Map> categoryMapList = categoryService.listCategoryArticleCount(null);
                potalDataMap.put("categoryMapList", categoryMapList);
            }
        } else {
            potalDataMap = new HashMap();
            // 博客归档分类信息
            List<Map> categoryMapList = categoryService.listCategoryArticleCount(null);
            potalDataMap.put("categoryMapList", categoryMapList);
            session.setAttribute("potalDataMap", potalDataMap);
        }
    }

    /**
     * 异步花心redis session 会话信息
     *
     * @param request
     * @param response
     * @param handler
     */
    public void refreshRedisSession(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String userSessionToken = CookieUtils.getCookieValue(request, sysConfigAttribute.getSessionTokenName());
        if (StrUtil.isNotEmpty(userSessionToken)) {
            // 更新redis中session时间 可以 使用异步处理
            userService.updateRedisUserSessionExpireTime(userSessionToken, SysConstant.USER_SESSION_REDIS_TIME);
        }
    }

    /**
     * 获取用户信息 从cookie的 xwder-token redis获取用户信息写入session
     *
     * @param request
     * @param response
     * @param handler
     */
    public void getLoginUser(HttpServletRequest request, HttpServletResponse response, Object handler) {
        User sessionUser = (User) SessionUtil.getSessionAttribute(SysConstant.SESSION_USER);
        if (sessionUser == null) {
            String userSessionToken = CookieUtils.getCookieValue(request, sysConfigAttribute.getSessionTokenName());
            if (StrUtil.isNotEmpty(userSessionToken)) {
                // 从redis中获取
                User redisSessionUser = userService.getUserSessionFromRedis(userSessionToken);
                if (redisSessionUser != null) {
                    // session 写入用户信息
                    SessionUtil.setSessionAttribute(SysConstant.SESSION_USER, redisSessionUser);
                }
            }
        }
    }
}

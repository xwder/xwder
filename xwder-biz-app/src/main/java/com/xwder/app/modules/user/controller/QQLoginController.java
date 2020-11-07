package com.xwder.app.modules.user.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.xwder.app.attribute.SysConfigAttribute;
import com.xwder.app.consts.SysConstant;
import com.xwder.app.modules.user.entity.User;
import com.xwder.app.modules.user.entity.UserQQ;
import com.xwder.app.modules.user.service.intf.UserService;
import com.xwder.app.modules.user.service.login.QQLoginService;
import com.xwder.app.utils.CookieUtils;
import com.xwder.app.utils.SessionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;


/**
 * QQ第三方登录认证
 *
 * @author xwder
 * @version 1.0
 * @date 2020-11-06 19:28
 */
@Slf4j
@RequestMapping(value = {"/login/qq"})
@Controller
public class QQLoginController {

    @Autowired
    private SysConfigAttribute sysConfigAttribute;

    @Autowired
    private QQLoginService qqLoginService;

    @Autowired
    private UserService userService;


    /**
     * QQ获取授权回调接口 /login/qq/getauthcode
     *
     * @param code
     * @return
     */
    //@ResponseBody
    @RequestMapping("/getauthcode")
    public Object getAuthCode(String code, HttpServletRequest request, HttpServletResponse response) {
        // 获取当前登录域名设置 QQ登录的请求地址 因为可能会部署多个域名
        StringBuffer url = request.getRequestURL();
        String tempContextUrl = url.delete(url.length() - request.getRequestURI().length(), url.length()).append("/").toString();
        log.info("获取到QQ getauthcode 回调参数 code:{}", code);
        // 获取accessToken
        String accessToken = qqLoginService.getAccessToken(code,tempContextUrl);
        if (StrUtil.isEmpty(accessToken)) {
            return "redirect:/index.html";
        }
        // 获取用户OpenId
        String openID = qqLoginService.getOpenID(accessToken);
        UserQQ userQQ = qqLoginService.getUserInfo(accessToken, openID);
        if (userQQ == null || StrUtil.isEmpty(userQQ.getOpenId())) {
            log.error("获取QQ用户信息失败");
            return "redirect:/index.html";
        }
        // 根据openId查询qq用户信息表 查到更新没查到创建
        UserQQ existUserQQ = qqLoginService.findByOpenId(userQQ.getOpenId());
        if (existUserQQ == null) {
            existUserQQ = qqLoginService.saveOrUpdateUserQQ(userQQ);
        } else {
            // 忽略null字段和错误字段
            BeanUtil.copyProperties(userQQ, existUserQQ, CopyOptions.create().setIgnoreNullValue(true).setIgnoreError(true));
            qqLoginService.saveOrUpdateUserQQ(existUserQQ);
        }
        // 根据QQ用户信息openId查询用户表
        User user = userService.findByOpenId(existUserQQ.getOpenId());
        if (user == null) {
            // 用户不存在
            user = userService.createUserByUserQQ(userQQ);
        } else {
            user.setLastLoginTime(new Date());
            userService.saveOrUpdateUser(user);
            user.setPassword(null);
            user.setSalt(null);
        }
        String xwderToken = RandomUtil.randomString(32);
        // session 写入用户信息
        SessionUtil.setSessionAttribute(SysConstant.SESSION_USER, user);
        // cookie 写人 认证 xwder-token
        CookieUtils.setCookie(request, response, sysConfigAttribute.getSessionTokenName(), xwderToken);
        // 写入redis session
        userService.saveUserSessionToRedis(xwderToken, user, SysConstant.USER_SESSION_REDIS_TIME);

        // 获取用户信息后跳转到对应页面
        return "redirect:/index.html";
    }


}

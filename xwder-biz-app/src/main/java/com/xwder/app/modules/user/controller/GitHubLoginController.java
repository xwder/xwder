package com.xwder.app.modules.user.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.xwder.app.attribute.SysConfigAttribute;
import com.xwder.app.consts.SysConstant;
import com.xwder.app.modules.user.entity.User;
import com.xwder.app.modules.user.entity.UserGithub;
import com.xwder.app.modules.user.entity.UserQQ;
import com.xwder.app.modules.user.service.intf.UserService;
import com.xwder.app.modules.user.service.login.GitHubLoginService;
import com.xwder.app.utils.CookieUtils;
import com.xwder.app.utils.HttpServletRequestUtil;
import com.xwder.app.utils.SessionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * github第三方登录认证
 *
 * @author xwder
 * @version 1.0
 * @date 2020-11-08 14:55
 */
@Slf4j
@RequestMapping(value = {"/login/github"})
@Controller
public class GitHubLoginController {

    @Autowired
    private SysConfigAttribute sysConfigAttribute;

    @Autowired
    private GitHubLoginService gitHubLoginService;

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
        if (StrUtil.isEmpty(code)) {
            return "redirect:/index.html";
        }
        String domain = HttpServletRequestUtil.getDomain(request);
        String accessToken = gitHubLoginService.getAccessToken(code, domain);
        if (StrUtil.isEmpty(accessToken)) {
            return "redirect:/index.html";
        }
        UserGithub userGithub = gitHubLoginService.getUserInfo(accessToken);
        if (userGithub == null) {
            return "redirect:/index.html";
        }
        // 根据 login githu用户名字段 查询 github用户信息表 查到更新没查到创建
        UserGithub existUserGithub = gitHubLoginService.findByGithubName(userGithub.getLogin());
        if (existUserGithub == null) {
            gitHubLoginService.saveOrUpdateUserGithub(userGithub);
        } else {
            // 忽略null字段和错误字段
            BeanUtil.copyProperties(userGithub, existUserGithub, CopyOptions.create().setIgnoreNullValue(true).setIgnoreError(true));
            gitHubLoginService.saveOrUpdateUserGithub(existUserGithub);
        }
        // 根据 github_user_name 查询qq用户信息表 查到更新没查到创建
        User user = userService.findByGithubUserName(existUserGithub.getLogin());

        if (user == null) {
            // 用户不存在
            user = userService.createUserByUserGithub(existUserGithub);
        } else {
            user.setLastLoginTime(new Date());
            userService.saveOrUpdateUser(user);
            user.setPassword(null);
            user.setSalt(null);
            user.setAvatar(existUserGithub.getAvatarUrl());
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

package com.xwder.app.modules.user.controller;

import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.kaptcha.Kaptcha;
import com.xwder.app.attribute.SysConfigAttribute;
import com.xwder.app.common.result.CommonResult;
import com.xwder.app.common.result.ResultCode;
import com.xwder.app.consts.SysConfigConstants;
import com.xwder.app.modules.user.entity.User;
import com.xwder.app.modules.user.service.intf.UserService;
import com.xwder.app.modules.user.service.login.GitHubLoginService;
import com.xwder.app.modules.user.service.login.QQLoginService;
import com.xwder.app.utils.AssertUtil;
import com.xwder.app.utils.CookieUtils;
import com.xwder.app.utils.HttpServletRequestUtil;
import com.xwder.app.utils.SessionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * @author xwder
 * @Description: 登录注册
 * @date 2020/7/11 18:41
 */
@Slf4j
@Controller
public class LoginRegisterController {


    @Autowired
    private Kaptcha kaptcha;

    @Autowired
    private UserService userService;

    @Autowired
    private SysConfigAttribute sysConfigAttribute;

    @Autowired
    private QQLoginService qqLoginService;

    @Autowired
    private GitHubLoginService gitHubLoginService;

    /**
     * 登录页面
     *
     * @return
     */
    @RequestMapping("/login")
    public String toLong(HttpServletRequest request, Model model) {
        // 获取当前登录域名设置 QQ登录的请求地址 因为可能会部署多个域名
        String tempContextUrl = HttpServletRequestUtil.getDomain(request);
        String qqLoginUrl = qqLoginService.getQQLoginUrl(tempContextUrl);
        model.addAttribute("qqLoginUrl", qqLoginUrl);
        model.addAttribute("githubLoginUrl", gitHubLoginService.getGitHubLoginUrl());
        return "login";
    }

    /**
     * 注册页面
     *
     * @return
     */
    @RequestMapping("/register")
    public String toRegister() {

        return "register";
    }

    /**
     * 注册校验用户名是否存在
     *
     * @param userName
     * @return
     */
    @ResponseBody
    @PostMapping("/checkUserName")
    public Object checkUserNameExist(@RequestParam("userName") String userName) {
        AssertUtil.paramIsNotNull(userName, "用户名不能为空");
        User existUser = userService.getUserByUserName(userName);
        AssertUtil.isNull(existUser, (int) ResultCode.PARAM_VALIDATE_FAILD.getCode(), "用户{}已存在 ", userName);
        return CommonResult.success();
    }

    /**
     * 注册校验邮箱是否已经存在
     *
     * @param email
     * @return
     */
    @ResponseBody
    @PostMapping("/checkEmail")
    public Object checkEmailExist(@RequestParam("email") String email) {
        AssertUtil.paramIsNotTrue(Validator.isEmail(email), "邮箱{}格式校验失败", email);
        User existUser = userService.getUserByUserEmail(email);
        AssertUtil.isNull(existUser, (int) ResultCode.PARAM_VALIDATE_FAILD.getCode(), "邮箱{}已存在 ", email);
        return CommonResult.success();
    }

    /**
     * 注册用户
     *
     * @param user
     * @param request
     * @return
     */
    @ResponseBody
    @PostMapping("/doRegister")
    public Object userRegister(User user, HttpServletRequest request) {
        AssertUtil.paramIsNotNull(user.getUserName(), "用户名不能为空");
        AssertUtil.paramIsNotNull(user.getPassword(), "密码不能为空");
        AssertUtil.paramIsNotTrue(Validator.isEmail(user.getEmail()), "邮箱{}格式校验失败", user.getEmail());

        if (user.getId() == null) {
            // 注册逻辑 先检查 用户名 邮箱 是否存在
            User existUser = userService.getUserByUserName(user.getUserName());
            if (existUser != null) {
                log.info("注册用户用户名[{}]已经存在", user.getUserName());
                return CommonResult.failed("注册用户用户名 " + user.getUserName() + " 已经存在");
            }
            existUser = userService.getUserByUserEmail(user.getEmail());
            if (existUser != null) {
                log.info("注册名邮箱 " + user.getEmail() + " 已经存在", user.getEmail());
                return CommonResult.failed("注册失败");
            }
        }
        User resultUser = userService.saveOrUpdateUser(user);
        if (resultUser == null) {
            return CommonResult.failed("注册失败");
        }
        CommonResult<User> commonResult = CommonResult.success(resultUser);
        return commonResult;
    }

    /**
     * 登录
     *
     * @param user
     * @param request
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/doLogin")
    public Object userLogin(User user, String redirect, HttpServletRequest request, HttpServletResponse response) {
        AssertUtil.paramIsNotNull(user.getUserName(), "用户名不能为空");
        AssertUtil.paramIsNotNull(user.getPassword(), "密码不能为空");
        User resultUser = userService.userLogin(user);
        if (resultUser == null) {
            return CommonResult.failed("登录失败");
        }

        String xwderToken = RandomUtil.randomString(32);
        // session 写入用户信息
        SessionUtil.setSessionAttribute(SysConfigConstants.SESSION_USER, resultUser);
        // cookie 写人 认证 xwder-token
        CookieUtils.setCookie(request, response, sysConfigAttribute.getSessionTokenName(), xwderToken);
        // 写入redis session
        userService.saveUserSessionToRedis(xwderToken, resultUser, SysConfigConstants.USER_SESSION_REDIS_TIME);

        CommonResult<User> commonResult = CommonResult.success(resultUser);
        return commonResult;
    }

    /**
     * 登录
     *
     * @param request
     * @param response
     * @return
     */
    @GetMapping(value = "/doLoginOut")
    public String loginOut(HttpServletRequest request, HttpServletResponse response) {
        String xwderToken = CookieUtils.getCookieValue(request, sysConfigAttribute.getSessionTokenName());
        // session 删除 用户信息
        SessionUtil.removeSessionAttribute(SysConfigConstants.SESSION_USER);
        // cookie 删除 认证 xwder-token
        CookieUtils.deleteCookie(request, response, xwderToken);
        // 删除 redis 用户信息
        userService.deleteRedisUserSession(xwderToken);

        return "redirect:/index.html";
    }

    /**
     * 邮箱激活校验
     *
     * @param verifyKey
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/mail/verifyMail", produces = "application/json")
    public Object verifyEmail(@RequestParam("verifyKey") String verifyKey) {
        AssertUtil.paramIsNotNull(verifyKey, "verifyKey不能为空");
        User user = userService.verifyEmail(verifyKey);
        if (user == null) {
            return CommonResult.failed("邮箱验证失败");
        }
        return CommonResult.success(String.format("用户:{}邮箱{}激活成功", user.getUserName(), user.getEmail()));
    }

    /**
     * 图形验证码
     */
    @GetMapping("/sendVerifyCode")
    public void checkUserNameExist() {
        kaptcha.render();
    }

    /**
     * 先校验图形验证码 10S有效 然后发送短信验证码
     *
     * @param verifyCode
     * @param phone
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/verifyCodeValidAndSendSmsVerifyCode")
    public CommonResult verifyCodeValidAndSendSmsVerifyCode(@RequestParam String verifyCode, @RequestParam("phone") String phone, HttpServletRequest request) {
        AssertUtil.paramIsNotNull(verifyCode, "验证码不能为空");
        AssertUtil.paramIsNotTrue(Validator.isMobile(phone), "手机号码{}格式校验失败", phone);
        kaptcha.validate(verifyCode, 10);
        // 验证成功 发送短信验证码
        HttpSession session = request.getSession();
        String smsVerifyCode = RandomUtil.randomNumbers(6);
        Date date = new Date();
        // 发送短信验证码 TODO
        session.setAttribute("smsVerifyCode", smsVerifyCode);
        session.setAttribute("smsVerifyTime", date);
        return CommonResult.success("短信验证码发送成功");
    }

}

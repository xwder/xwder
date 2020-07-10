package com.xwder.app.modules.user.controller;

import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.kaptcha.Kaptcha;
import com.xwder.app.modules.user.entity.User;
import com.xwder.app.modules.user.service.intf.UserService;
import com.xwder.app.utils.AssertUtil;
import com.xwder.cloud.commmon.api.CommonResult;
import com.xwder.cloud.commmon.api.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.Random;

/**
 * @author wande
 * @version 1.0
 * @date 2020/07/09
 */
@RequestMapping("/user")
@RestController
public class UserController {

    @Autowired
    private Kaptcha kaptcha;

    @Autowired
    private UserService userService;

    @PostMapping("/checkUserId")
    public Object checkUserIdExist(@RequestParam("userId") String userId) {
        AssertUtil.paramIsNotNull(userId, "用户名不能为空");
        User existUser = userService.getUserByUserUserId(userId);
        AssertUtil.isNull(existUser, (int) ResultCode.PARAM_VALIDATE_FAILD.getCode(), "用户{}已存在 ", userId);
        return CommonResult.success();
    }

    @PostMapping("/register")
    public Object userRegister(User user) {
        AssertUtil.paramIsNotNull(user.getUserId(), "用户名不能为空");
        AssertUtil.paramIsNotNull(user.getPassword(), "密码不能为空");
        AssertUtil.paramIsNotTrue(Validator.isEmail(user.getEmail()), "邮箱{}格式校验失败", user.getEmail());
        User resultUser = userService.saveOrUpdateUser(user);
        if (resultUser == null) {
            return CommonResult.failed("操作失败");
        }
        CommonResult<User> commonResult = CommonResult.success(resultUser);
        return commonResult;
    }

    @RequestMapping("/mail/verifyMail")
    public Object verifyEmail(@RequestParam("verifyKey") String verifyKey) {
        AssertUtil.paramIsNotNull(verifyKey, "verifyKey不能为空");
        User user = userService.verifyEmail(verifyKey);
        if (user == null) {
            return CommonResult.failed("邮箱验证失败");
        }
        return CommonResult.success(user);
    }

    /**
     * 图形验证码
     */
    @GetMapping("/sendVerifyCode")
    public void checkUserIdExist() {
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

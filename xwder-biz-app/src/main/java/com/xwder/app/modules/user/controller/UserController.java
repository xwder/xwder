package com.xwder.app.modules.user.controller;

import cn.hutool.core.lang.Validator;
import com.xwder.app.modules.user.entity.User;
import com.xwder.app.modules.user.service.intf.UserService;
import com.xwder.app.utils.AssertUtil;
import com.xwder.cloud.commmon.api.CommonResult;
import com.xwder.cloud.commmon.api.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wande
 * @version 1.0
 * @date 2020/07/09
 */
@RequestMapping("/user")
@RestController
public class UserController {

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
        AssertUtil.paramIsNotNull(user.getEmail(), "邮箱不能为空");
        AssertUtil.paramIsNotTrue(Validator.isEmail(user.getEmail()),"邮箱{}格式校验失败",user.getEmail());
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
}

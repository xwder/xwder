package com.xwder.app.modules.user.controller;

import com.xwder.app.modules.user.entity.User;
import com.xwder.app.modules.user.service.intf.UserService;
import com.xwder.cloud.commmon.api.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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

    @PostMapping("/register")
    public Object userRegister(User user) {
        User resultUser = userService.saveOrUpdateUser(user);
        if (resultUser == null) {
            return CommonResult.failed("操作失败");
        }
        CommonResult<User> commonResult = CommonResult.success(resultUser);
        return commonResult;
    }

    @RequestMapping("/mail/verifyMail")
    public Object verifyEmail(@RequestParam("verifyKey") String verifyKey) {
        User user = userService.verifyEmail(verifyKey);
        if (user == null) {
            return CommonResult.failed("邮箱验证失败");
        }
        return CommonResult.success(user);
    }
}

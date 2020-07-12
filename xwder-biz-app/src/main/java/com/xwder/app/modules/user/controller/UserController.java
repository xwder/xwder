package com.xwder.app.modules.user.controller;

import com.baomidou.kaptcha.Kaptcha;
import com.xwder.app.modules.user.service.intf.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author wande
 * @version 1.0
 * @date 2020/07/09
 */
@Slf4j
@RequestMapping("/user")
@Controller
public class UserController {

    @Autowired
    private Kaptcha kaptcha;

    @Autowired
    private UserService userService;

    /**
     * 用户首页
     *
     * @param modelAndView
     * @return
     */
    @RequestMapping("")
    private String toUser(ModelAndView modelAndView) {


        return "user/user";
    }


}

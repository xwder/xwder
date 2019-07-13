package com.xwder.manage.module.system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ClassName: HomeController
 * @Description:
 * @Author: xwder
 * @Date: 2019年7月9日22:24:45
 * @Version: 1.0
 */
@Controller
public class HomeController {

    @RequestMapping("/index")
    public String index() {
        return "index";
    }

    @GetMapping("/dashboard/main")
    public String main(Model model) {
        return "main";
    }

    @GetMapping("/menuItem")
    public String menuItem1(Model model) {
        return "menuitem/menuItem";
    }
}

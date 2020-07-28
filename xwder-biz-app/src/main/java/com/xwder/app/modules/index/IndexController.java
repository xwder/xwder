package com.xwder.app.modules.index;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author xwder
 * @Description: 首页
 * @date 2020/7/11 19:50
 */
@Controller
public class IndexController {

    @RequestMapping(value = {"/", "/index"})
    public String index(Model model) {
        return "index";
    }
}

package com.xwder.app.modules.index;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author xwder
 * @Description: 首页
 * @date 2020/7/11 19:50
 */
@Controller
public class IndexController {

    @RequestMapping("/index")
    public String index(ModelAndView model){

        return "index";
    }
}

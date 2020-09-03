package com.xwder.app.sysmodules;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author wande
 * @version 1.0
 * @date 2020/09/01
 */
@Controller
@RequestMapping(value = "/sys")
public class SysIndexController {

    @RequestMapping(value = {"/", "/index"})
    public String sysIndex(){
        return "sys/index";
    }

    @RequestMapping(value = { "/main"})
    public String sysMain(){
        return "sys/main";
    }
}

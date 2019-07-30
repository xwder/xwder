package com.xwder.monitor.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * HystrixDashboard index
 *
 * @author xwder
 */
@Controller
public class HystrixIndexController {
    @GetMapping("")
    public String index() {
        return "forward:/hystrix";
    }
}
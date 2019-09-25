package com.xwder.cloudgateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * fallback
 *
 * @author xwder
 * @date 2019-9-25 23:07:23
 */
@RestController
public class FallbackController {

    @GetMapping("/fallback")
    public String fallback() {
        return "fallback";
    }

}

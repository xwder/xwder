package com.xwder.cloud.gateway.controller;

import com.xwder.cloud.commmon.api.CommonResult;
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
    public CommonResult fallback() {
        return CommonResult.failed();
    }

}

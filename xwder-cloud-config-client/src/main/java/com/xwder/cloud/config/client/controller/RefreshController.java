package com.xwder.cloud.config.client.controller;

import com.xwder.cloud.config.client.config.Xwder2Properties;
import com.xwder.cloud.config.client.config.XwderProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * @author wande
 * @version 1.0
 * @date 2020/04/27
 */
@RefreshScope
@RestController
public class RefreshController {

    @Autowired
    private XwderProperties xwderProperties;

    @Autowired
    private Xwder2Properties xwder2Properties;

    @Value("${xwder.username}")
    private String username = "username";

    /**
     * 刷新配置文件 返回新获取到的配置信息
     *
     * @return
     */
    @RequestMapping("/username")
    public Object getUserName() {
        HashMap<Object, Object> resultMap = new HashMap<>();
        resultMap.put("username", xwderProperties.getUsername());
        return resultMap;
    }

    /**
     * 刷新配置文件 返回新获取到的配置信息
     *
     * @return
     */
    @RequestMapping("/username2")
    public Object getUserName2() {
        HashMap<Object, Object> resultMap = new HashMap<>();
        resultMap.put("username", xwder2Properties.getUsername());
        return resultMap;
    }

    /**
     * 刷新配置文件 返回新获取到的配置信息
     *
     * @return
     */
    @RequestMapping("/username3")
    public Object getUserName3() {
        HashMap<Object, Object> resultMap = new HashMap<>();
        resultMap.put("username", username);
        return resultMap;
    }
}

package com.xwder.app.sysmodules.file.controller;

import com.xwder.app.modules.blog.service.intf.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author xwder
 * @Description:
 * @date 2020/7/22 23:58
 */
@RequestMapping(value = "/ueditor")
@Controller
public class UeDitorController {

    @Autowired
    private ArticleService articleService;

    /**
     * ueditor 配置 和 内部静态静态资源地址配置 和 对应的 static/ueditor 请求对应
     *
     * @param request
     * @param action
     * @return
     */
    @RequestMapping("")
    @ResponseBody
    public Map getUeDitorUploadConfigDto(HttpServletRequest request, String action) {
        Map<String, Object> resultMap = articleService.getUeDitorUploadConfig();
        return resultMap;
    }
}

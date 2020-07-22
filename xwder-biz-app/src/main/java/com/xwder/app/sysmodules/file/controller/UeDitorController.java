package com.xwder.app.sysmodules.file.controller;

import com.xwder.app.modules.blog.service.intf.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xwder
 * @Description:
 * @date 2020/7/22 23:58
 */
@Controller
public class UeDitorController {

    @Autowired
    private ArticleService articleService;

    @RequestMapping("/ueditor")
    @ResponseBody
    public Map getUeDitorUploadConfigDto(HttpServletRequest request, String action) {
        Map<String, Object> resultMap = new HashMap<>();
        if ("config".equals(action)) {
            resultMap = articleService.getUeDitorUploadConfig();
        }
        if ("uploadimage".equals(action)) {
            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
            MultipartFile multipartFile = multipartHttpServletRequest.getFile("file");
            File file = new File("D://" + multipartFile.getOriginalFilename());
            try {
                multipartFile.transferTo(file);
                resultMap.put("state", "SUCCESS");
                resultMap.put("url", "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=3160088306,3240134621&fm=26&gp=0.jpg");
                resultMap.put("title", file.getName());
                resultMap.put("original", file.getName());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return resultMap;
    }
}

package com.xwder.app.sysmodules.file.controller;

import cn.hutool.core.util.StrUtil;
import com.xwder.app.sysmodules.file.service.intf.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xwder
 * @Description:
 * @date 2020/7/22 23:58
 */

@Controller
@RequestMapping(value = "/file")
public class FileUploadController {

    @Autowired
    private FileUploadService fileUploadService;

    @RequestMapping("/up/ue")
    @ResponseBody
    public Map ueditorFileUpload(HttpServletRequest request, String action) {

        Map<String, Object> resultMap = new HashMap<>();
        if (StrUtil.equalsAnyIgnoreCase(action, "uploadimage", "uploadscrawl")) {
            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
            MultipartFile multipartFile = multipartHttpServletRequest.getFile("file");
            resultMap = fileUploadService.ueDitorFileUpload(multipartFile);
        }
        return resultMap;
    }


}

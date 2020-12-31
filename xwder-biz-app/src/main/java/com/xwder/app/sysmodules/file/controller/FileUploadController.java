package com.xwder.app.sysmodules.file.controller;

import cn.hutool.core.util.StrUtil;
import com.xwder.app.common.result.CommonResult;
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

    /**
     * udeitor 编辑器博客文章图片上传接口
     *
     * @param request
     * @param action
     * @return
     */
    @RequestMapping("/up/ue")
    @ResponseBody
    public Map ueditorBlogImageFileUpload(HttpServletRequest request, String action) {

        Map<String, Object> resultMap = new HashMap<>();
        // ueditor 博客文章编辑图片上传
        if (StrUtil.equalsAnyIgnoreCase(action, "uploadimage", "uploadscrawl")) {
            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
            MultipartFile multipartFile = multipartHttpServletRequest.getFile("file");
            resultMap = fileUploadService.ueditorBlogImageFileUpload(multipartFile);
        }
        return resultMap;
    }

    /**
     * udeitor 编辑器博客文章图片上传接口
     *
     * @param request
     * @param type 来源 blog
     * @return
     */
    @RequestMapping("/up/editormd")
    @ResponseBody
    public Map editormdBlogImageFileUpload(HttpServletRequest request, String type) {

        Map<String, Object> resultMap = new HashMap<>();
        // ueditor 博客文章编辑图片上传
        if (StrUtil.equalsAnyIgnoreCase(type, "blog")) {
            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
            MultipartFile multipartFile = multipartHttpServletRequest.getFile("editormd-image-file");
            resultMap = fileUploadService.editormdBlogImageFileUpload(multipartFile);
        }
        return resultMap;
    }

    /**
     * cos文件删除接口
     *
     * @param request
     * @param fileKey
     * @return
     */
    @RequestMapping("/deleteCosFile")
    @ResponseBody
    public CommonResult deleteCosFile(HttpServletRequest request, String fileKey) {
        CommonResult commonResult = fileUploadService.deleteCosFile(fileKey);
        return commonResult;
    }
}

package com.xwder.app.modules.cloud.tencent.docsimg.controller;

import cn.hutool.core.util.StrUtil;
import com.xwder.app.modules.cloud.tencent.docsimg.service.QqDocsImageUploadService;
import com.xwder.app.sysmodules.file.entity.FileUploadRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author wande
 * @version 1.0
 * @date 2020/09/01
 */
@RequestMapping("/tencent/docs/image")
@RestController
public class QqDocsImageUploadController {

    @Autowired
    private QqDocsImageUploadService qqDocsImageUploadService;

    @PostMapping("/upload")
    public Object qqDocsImageUpload(HttpServletRequest request) {
        // cookie腾讯文档身份认证cookie
        String uid = request.getParameter("uid");
        String uidKey = request.getParameter("uidKey");
        // 保存到本地 1-保存 0-不保存
        Integer saveLocal = StrUtil.equals(request.getParameter("saveLocal"), "1") ? 1 : 0;
        // 上传cos 1-上传 0-不上传
        Integer uploadCos = StrUtil.equals(request.getParameter("uploadCos"), "1") ? 1 : 0;
        MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
        MultipartFile multipartFile = multipartHttpServletRequest.getFile("file");
        FileUploadRecord fileUploadRecord = qqDocsImageUploadService.qqDocsImageUpload(saveLocal, uploadCos, uid, uidKey, multipartFile);
        return fileUploadRecord;
    }
}

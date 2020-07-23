package com.xwder.app.sysmodules.file.service.intf;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * 文件上传service
 */
public interface FileUploadService {


    /**
     * ueditor 上传文件 保存到服务 然后上传 cos
     *
     * @param multipartFile
     * @return ueditor 上传标准响应 文件地址为cdn文件地址
     */
    Map ueDitorFileUpload(MultipartFile multipartFile);
}

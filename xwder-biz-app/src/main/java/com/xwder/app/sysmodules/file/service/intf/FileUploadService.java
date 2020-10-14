package com.xwder.app.sysmodules.file.service.intf;

import com.xwder.app.common.result.CommonResult;
import com.xwder.app.sysmodules.file.entity.FileUploadRecord;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * 文件上传service
 * @author wande
 * @version 1.0
 * @date 2020/07/23
 */
public interface FileUploadService {


    /**
     * ueditor 博客文章图片上传文件 保存到服务 然后上传 cos
     *
     * @param multipartFile
     * @return ueditor 上传标准响应 文件地址为cdn文件地址
     */
    Map ueditorBlogImageFileUpload(MultipartFile multipartFile);

    /**
     * 上传文件 到cos
     * 例如 https://cdn.xwder.com/image/blog/xwder/1-20201010094334764.png
     * @param multipartFile 上传文件
     * @param uploadDir /image/blog/xwder
     * @param fileName 1-20201010094334764.png
     * @param isSaveFile 是否保存文件在磁盘
     * @return
     */
    FileUploadRecord uploadFileCos(MultipartFile multipartFile,String uploadDir,String fileName,Boolean isSaveFile);

    /**
     * 删除cos文件
     * @param fileKey  image/blog/xwder/1-20201010094334764.png
     * @return
     */
    CommonResult deleteCosFile(String  fileKey);
}

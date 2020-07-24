package com.xwder.app.sysmodules.file.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.xwder.app.attribute.SysConfigAttribute;
import com.xwder.app.consts.SysConstant;
import com.xwder.app.modules.cloud.tencent.cos.service.TencentCosService;
import com.xwder.app.modules.user.entity.User;
import com.xwder.app.sysmodules.file.entity.FileUploadRecord;
import com.xwder.app.sysmodules.file.service.intf.FileUploadRecordService;
import com.xwder.app.sysmodules.file.service.intf.FileUploadService;
import com.xwder.app.utils.DateUtil;
import com.xwder.app.utils.SessionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 文件上传service
 *
 * @author wande
 * @version 1.0
 * @date 2020/07/23
 */
@Slf4j
@Service
public class FileUploadServiceImpl implements FileUploadService {

    @Autowired
    private FileUploadRecordService fileUploadRecordService;

    @Autowired
    private SysConfigAttribute sysConfigAttribute;

    @Autowired
    private TencentCosService tencentCosService;

    /**
     * ueditor 上传文件 保存到服务 然后上传 cos
     *
     * @param multipartFile
     * @return ueditor 上传标准响应 文件地址为cdn文件地址
     */
    @Override
    public Map ueDitorFileUpload(MultipartFile multipartFile) {
        String fileName = multipartFile.getOriginalFilename();
        // 文件上传目录 服务器上和cos上路径保持一致
        String saveFileDir = serverSaveFileDir(fileName);
        String serverSaveCompleteDir = sysConfigAttribute.getUploadSaveBaseDir() + File.separator + saveFileDir;
        File serverSaveFile = new File(serverSaveCompleteDir);
        File parentFile = serverSaveFile.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("state", "上传失败");
        resultMap.put("url", null);
        resultMap.put("title", null);
        resultMap.put("original", null);
        try {
            multipartFile.transferTo(serverSaveFile);
        } catch (IOException e) {
            log.error("保存文件失败", e);
            return resultMap;
        }
        log.info("保存文件[{}]到磁盘成功", serverSaveFile.getName());
        saveFileDir = saveFileDir.replaceAll("\\\\","/");
        String cosUploadResult = tencentCosService.uploadTenCos(serverSaveFile, saveFileDir, sysConfigAttribute.getTencentCosBucketName(), sysConfigAttribute.getTencentCosRegion());
        if (StrUtil.isEmpty(cosUploadResult)) {
            log.error("保存文件[{}]到cos失败", serverSaveFile.getName());
            // 删除磁盘文件
            FileUtil.del(serverSaveFile);
            resultMap.put("state", "上传对象存储失败");
            return resultMap;
        }
        String fileCdnUrl = sysConfigAttribute.getTencentCdnPreFixUrl() + "/" + saveFileDir;
        log.error("保存文件[{}]到cos成功，cdn访问地址[{}]", serverSaveFile.getName(), fileCdnUrl);

        // 保存文件上传记录
        FileUploadRecord fileUploadRecord = new FileUploadRecord();
        User sessionUser = (User) SessionUtil.getSessionAttribute(SysConstant.SESSION_USER);
        fileUploadRecord.setUserId(sessionUser.getId());
        fileUploadRecord.setUserName(sessionUser.getUserName());
        fileUploadRecord.setFileType(getFileType(fileName));
        fileUploadRecord.setFileSize(FileUtil.size(serverSaveFile));
        fileUploadRecord.setLocalUrl(serverSaveCompleteDir);
        fileUploadRecord.setCosUrl(fileCdnUrl);
        Date date = new Date();
        fileUploadRecord.setGmtCreate(date);
        fileUploadRecord.setGmtModified(date);
        fileUploadRecordService.save(fileUploadRecord);

        resultMap.put("state", "SUCCESS");
        resultMap.put("url", fileCdnUrl);
        resultMap.put("title", serverSaveFile.getName());
        resultMap.put("original", fileCdnUrl);
        return resultMap;
    }

    /**
     * 文件上传地址
     *
     * @param fileName
     * @return
     */
    private String serverSaveFileDir(String fileName) {
        User sessionUser = (User) SessionUtil.getSessionAttribute(SysConstant.SESSION_USER);
        String[] splits = fileName.split("\\.");
        String fileSuffix = splits.length >= 2 ? splits[splits.length-1] : "";
        String fileType = getFileType(fileName);
        // 服务器保存地址 baseDir + 文件类型 + 用户主键-用户名 + 用户主键-当前时间 + 文件后缀
        String saveDir = fileType + File.separator + sessionUser.getId() + "-" + sessionUser.getUserName()
                + File.separator + sessionUser.getId() + "-" + DateUtil.formatDate(new Date(), DateUtil.format_yyyyMMddHHmmssSSS) + "." + fileSuffix;
        return saveDir;
    }

    /**
     * 获取文件类型
     *
     * @param fileName
     * @return
     */
    private String getFileType(String fileName) {
        String[] splits = fileName.split("\\.");
        String fileSuffix = splits.length >= 2 ? splits[splits.length-1] : "";
        String fileType = "file";
        // 判断文件类型 图片 视频
        if (StrUtil.equalsAnyIgnoreCase(fileSuffix, "png", "jpg", "jpeg", "gif", "bmp")) {
            fileType = "image";
        }
        return fileType;
    }
}

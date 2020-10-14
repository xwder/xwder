package com.xwder.app.sysmodules.file.service.intf;

import com.xwder.app.sysmodules.file.entity.FileUploadRecord;

/**
 * 文件上传记录service
 *
 * @author xwder
 */
public interface FileUploadRecordService {

    /**
     * 保存文件上传记录
     *
     * @param fileUploadRecord
     * @return
     */
    FileUploadRecord saveOrUpdate(FileUploadRecord fileUploadRecord);

    /**
     * 根据 userId 和cosUrl查询 FileUploadRecord
     * @param userId
     * @param cosUrl
     * @return
     */
    FileUploadRecord getFileUploadRecordByUserIdAndCosUrl(Long userId, String cosUrl);
}

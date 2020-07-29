package com.xwder.app.sysmodules.file.service.intf;

import com.xwder.app.sysmodules.file.entity.FileUploadBlock;

/**
 * 文件分块断点续传 service
 */
public interface FileUploadBlockService {

    /**
     * 根据文件的key查询文件上传信息
     *
     * @param fileKey
     * @return
     */
    FileUploadBlock checkFileUpload(String fileKey);


    /**
     * 新增或者修改 FileUploadBlock
     *
     * @param fileUploadBlock
     */
    void saveOrUpdateFileUploadBlock(FileUploadBlock fileUploadBlock);
}

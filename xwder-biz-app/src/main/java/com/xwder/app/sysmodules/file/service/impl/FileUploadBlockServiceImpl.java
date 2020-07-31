package com.xwder.app.sysmodules.file.service.impl;

import com.xwder.app.sysmodules.file.entity.FileUploadBlock;
import com.xwder.app.sysmodules.file.repository.FileUploadBlockRepository;
import com.xwder.app.sysmodules.file.service.intf.FileUploadBlockService;
import com.xwder.app.utils.UpdateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * 文件分块断点续传 service
 *
 * @author wande
 * @version 1.0
 * @date 2020/07/29
 */
@Service
public class FileUploadBlockServiceImpl implements FileUploadBlockService {

    @Autowired
    private FileUploadBlockRepository fileUploadBlockRepository;

    /**
     * 根据文件的key查询文件上传信息
     *
     * @param fileKey
     * @return
     */
    @Override
    public FileUploadBlock checkFileUpload(String fileKey) {
        return fileUploadBlockRepository.findByFileKey(fileKey);
    }

    /**
     * 根据fikeKey 新增或者修改 FileUploadBlock
     *
     * @param fileUploadBlock
     */
    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void saveOrUpdateFileUploadBlock(FileUploadBlock fileUploadBlock) {
        FileUploadBlock existFileUploadBlock = checkFileUpload(fileUploadBlock.getFileKey());
        if (existFileUploadBlock == null) {
            Date date = new Date();
            fileUploadBlock.setGmtCreate(date);
            fileUploadBlock.setGmtModified(date);
            fileUploadBlockRepository.save(fileUploadBlock);
            return;
        }
        UpdateUtil.copyNullProperties(fileUploadBlock, existFileUploadBlock);
        fileUploadBlockRepository.save(existFileUploadBlock);
    }
}

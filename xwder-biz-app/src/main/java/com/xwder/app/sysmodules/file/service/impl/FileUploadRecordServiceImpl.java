package com.xwder.app.sysmodules.file.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.xwder.app.attribute.SysConfigAttribute;
import com.xwder.app.modules.cloud.tencent.cos.service.TencentCosService;
import com.xwder.app.sysmodules.file.entity.FileUploadRecord;
import com.xwder.app.sysmodules.file.repository.FileUploadRecordRepository;
import com.xwder.app.sysmodules.file.service.intf.FileUploadRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author xwder
 * @Description: 文件上传记录Service
 * @date 2020/7/22 23:25
 */
@Slf4j
@Service
public class FileUploadRecordServiceImpl implements FileUploadRecordService {

    @Autowired
    private FileUploadRecordService fileUploadRecordService;

    @Autowired
    private SysConfigAttribute sysConfigAttribute;

    @Autowired
    private TencentCosService tencentCosService;

    @Autowired
    private FileUploadRecordRepository fileUploadRecordRepository;


    /**
     * 保存文件上传记录
     *
     * @param fileUploadRecord
     * @return
     */
    @Override
    @Transactional(rollbackFor = {Exception.class})
    public FileUploadRecord saveOrUpdate(FileUploadRecord fileUploadRecord) {
        return fileUploadRecordRepository.save(fileUploadRecord);
    }

    /**
     * 根据 userId 和cosUrl查询 FileUploadRecord
     * @param userId
     * @param cosUrl
     * @return
     */
    @Override
    public FileUploadRecord getFileUploadRecordByUserIdAndCosUrl(Long userId, String cosUrl) {
        List<FileUploadRecord> fileUploadRecordList = fileUploadRecordRepository.findByUserIdAndCosUrl(userId, cosUrl);
        if (CollectionUtil.isNotEmpty(fileUploadRecordList)) {
            return fileUploadRecordList.get(0);
        }
        return null;
    }
}

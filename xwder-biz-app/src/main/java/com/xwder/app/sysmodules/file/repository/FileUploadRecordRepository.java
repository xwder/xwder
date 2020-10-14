package com.xwder.app.sysmodules.file.repository;

import com.xwder.app.sysmodules.file.entity.FileUploadRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileUploadRecordRepository extends JpaRepository<FileUploadRecord, Long> {
    /**
     * 根据userid和cosurl查询 FileUploadRecord
     *
     * @param userId
     * @param cosUrl
     * @return
     */
    List<FileUploadRecord> findByUserIdAndCosUrl(Long userId, String cosUrl);
}

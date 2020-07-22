package com.xwder.app.sysmodules.file.repository;

import com.xwder.app.sysmodules.file.entity.FileUploadRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileUploadRecordRepository extends JpaRepository<FileUploadRecord, Long> {
}

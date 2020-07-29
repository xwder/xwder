package com.xwder.app.sysmodules.file.repository;

import com.xwder.app.sysmodules.file.entity.FileUploadBlock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileUploadBlockRepository extends JpaRepository<FileUploadBlock, Long> {

    /**
     * 根据file查询文件上传信息
     *
     * @param fileKey
     * @return
     */
    FileUploadBlock findByFileKey(String fileKey);

}

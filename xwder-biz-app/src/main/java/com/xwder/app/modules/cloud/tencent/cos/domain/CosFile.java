package com.xwder.app.modules.cloud.tencent.cos.domain;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * 腾讯云cosFile信息
 */
@Builder
@Data
public class CosFile {
    private String id;
    private String displayName;
    // 文件的路径key
    private String key;
    // 文件的etag
    private String etag;
    // 文件的长度
    private Long fileSize;
    // 文件的存储类型
    private String storageClasses;
    // 对象最后修改时间，为 ISO8601 格式，如2019-05-24T10:56:40Z
    private Date lastModified;
}

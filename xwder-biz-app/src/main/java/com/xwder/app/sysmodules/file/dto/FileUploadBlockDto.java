package com.xwder.app.sysmodules.file.dto;

import lombok.Data;

import java.util.Date;


@Data
public class FileUploadBlockDto {
    /**
     * id
     */
    private Integer id;

    /**
     * 相对路径
     */
    private String path;

    /**
     * 文件名
     */
    private String name;

    /**
     * 后缀
     */
    private String suffix;

    /**
     * 大小|字节B
     */
    private Integer size;


    /**
     * 创建时间
     */
    private Long createdAt;

    /**
     * 修改时间
     */
    private Long updatedAt;

    /**
     * 已上传分片
     */
    private Integer shardIndex;

    /**
     * 分片大小|B
     */
    private Integer shardSize;

    /**
     * 分片总数
     */
    private Integer shardTotal;

    /**
     * 文件标识
     */
    private String fileKey;

    /**
     * 备注
     */
    private String remark;

    private Date gmtCreate;

    private Date gmtModified;

}
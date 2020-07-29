package com.xwder.app.sysmodules.file.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author xwder
 * @Date 2020-07-29 11:25:31
 */
@Entity
@Table(name = "file_upload_block")
public class FileUploadBlock implements Serializable {

    private static final long serialVersionUID = 2337390360552999044L;

    /**
     * id
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 相对路径
     */
    @Column(name = "path")
    private String path;

    /**
     * 文件名
     */
    @Column(name = "name")
    private String name;

    /**
     * 源文件名
     */
    @Column(name = "source_name")
    private String sourceName;

    /**
     * 文件后缀
     */
    @Column(name = "suffix")
    private String suffix;

    /**
     * 文件大小|字节B
     */
    @Column(name = "size")
    private Long size;

    /**
     * 文件创建时间
     */
    @Column(name = "created_at")
    private Long createdAt;

    /**
     * 文件修改时间
     */
    @Column(name = "updated_at")
    private Long updatedAt;

    /**
     * 已上传分片
     */
    @Column(name = "shard_index")
    private Long shardIndex;

    /**
     * 分片大小|B
     */
    @Column(name = "shard_size")
    private Long shardSize;

    /**
     * 分片总数
     */
    @Column(name = "shard_total")
    private Long shardTotal;

    /**
     * 文件标识
     */
    @Column(name = "file_key")
    private String fileKey;

    /**
     * 备注
     */
    @Column(name = "remark")
    private String remark;

    @Column(name = "gmt_create")
    private Date gmtCreate;

    @Column(name = "gmt_modified")
    private Date gmtModified;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }


    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }


    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }


    public Long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Long updatedAt) {
        this.updatedAt = updatedAt;
    }


    public Long getShardIndex() {
        return shardIndex;
    }

    public void setShardIndex(Long shardIndex) {
        this.shardIndex = shardIndex;
    }


    public Long getShardSize() {
        return shardSize;
    }

    public void setShardSize(Long shardSize) {
        this.shardSize = shardSize;
    }


    public Long getShardTotal() {
        return shardTotal;
    }

    public void setShardTotal(Long shardTotal) {
        this.shardTotal = shardTotal;
    }


    public String getFileKey() {
        return fileKey;
    }

    public void setFileKey(String fileKey) {
        this.fileKey = fileKey;
    }


    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }


    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }


    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

}

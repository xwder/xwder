package com.xwder.app.sysmodules.file.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 文件上传记录
 *
 * @Author xwder
 * @Date 2020-07-22 23:19:58
 */
@Entity
@Table(name = "file_upload")
public class FileUploadRecord implements Serializable {

    private static final long serialVersionUID = 2132851722161011809L;

    /**
     * 主键ID
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 用户ID
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 文件类型
     */
    @Column(name = "file_type")
    private Integer fileType;

    /**
     * 文件大小单位KB
     */
    @Column(name = "file_size")
    private Long fileSize;

    /**
     * 存放于服务器的地址
     */
    @Column(name = "local_url")
    private String localUrl;

    /**
     * cos访问地址
     */
    @Column(name = "cos_url")
    private String cosUrl;

    /**
     * 文件组 比如m3u8多个文件 为同一组
     */
    @Column(name = "file_group")
    private String fileGroup;

    /**
     * 是否已删除默认为删除1 0-删除
     */
    @Column(name = "is_avaliable")
    private Integer isAvaliable;

    /**
     * 备注
     */
    @Column(name = "remark")
    private String remark;

    /**
     * 创建时间
     */
    @Column(name = "gmt_create")
    private Date gmtCreate;

    /**
     * 修改时间
     */
    @Column(name = "gmt_modified")
    private Date gmtModified;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }


    public Integer getFileType() {
        return fileType;
    }

    public void setFileType(Integer fileType) {
        this.fileType = fileType;
    }


    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }


    public String getLocalUrl() {
        return localUrl;
    }

    public void setLocalUrl(String localUrl) {
        this.localUrl = localUrl;
    }


    public String getCosUrl() {
        return cosUrl;
    }

    public void setCosUrl(String cosUrl) {
        this.cosUrl = cosUrl;
    }


    public String getFileGroup() {
        return fileGroup;
    }

    public void setFileGroup(String fileGroup) {
        this.fileGroup = fileGroup;
    }


    public Integer getIsAvaliable() {
        return isAvaliable;
    }

    public void setIsAvaliable(Integer isAvaliable) {
        this.isAvaliable = isAvaliable;
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

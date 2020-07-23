package com.xwder.app.sysmodules.file.dto;

import lombok.Data;

/**
 * UEditor 上传配置信息
 * UEditor 请求格式规范 config
 *
 * @author wande
 * @version 1.0
 * @date 2020/07/22
 */
@Data
public class UEditorUploadConfigDto {

    /**
     * 上传图片配置项
     */
    /* 执行上传图片的action名称 */
    private String imageActionName = "uploadimage";
    /* 提交的图片表单名称 */
    private String imageFieldName = "file";
    /* 上传大小限制，单位B */
    private Integer imageMaxSize = 4096000;
    /* 上传图片格式显示 */
    private String[] imageAllowFiles = {".png", ".jpg", ".jpeg", ".gif", ".bmp"};
    /* 是否压缩图片,默认是true */
    private Boolean imageCompressEnable = true;
    /* 图片压缩最长边限制 */
    private Integer imageCompressBorder = 1600;
    /* 插入的图片浮动方式 */
    private String imageInsertAlign = "none";
    /* 图片访问路径前缀 */
    private String imageUrlPrefix = "";
    /* 上传保存路径,可以自定义保存路径和文件名格式 */
    private String imagePathFormat = "/ueditor/jsp/upload/image/{yyyy}{mm}{dd}/{time}{rand:6}";

}

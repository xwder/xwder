package com.xwder.app.modules.blog.dto;

import lombok.Builder;
import lombok.Data;

/**
 *  需要支持callback参数,返回jsonp格式
 * {
 *     "imageUrl": "http://localhost/ueditor/php/controller.php?action=uploadimage",
 *     "imagePath": "/ueditor/php/",
 *     "imageFieldName": "upfile",
 *     "imageMaxSize": 2048,
 *     "imageAllowFiles": [".png", ".jpg", ".jpeg", ".gif", ".bmp"]
 * }
 * http://fex.baidu.com/ueditor/#dev-request_specification
 * UEditor 请求格式规范 config
 *
 * @author wande
 * @version 1.0
 * @date 2020/07/22
 */
@Data
public class UeDitorUploadConfigDto {

    private String imageUrl;
    private String imagePath;
    private String imageFieldName;
    private Integer imageMaxSize=5000;
    private String[] imageAllowFiles = {".png", ".jpg", ".jpeg", ".gif", ".bmp"};

}

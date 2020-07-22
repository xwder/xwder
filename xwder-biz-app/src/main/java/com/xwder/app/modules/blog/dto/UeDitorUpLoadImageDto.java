package com.xwder.app.modules.blog.dto;


import lombok.Builder;
import lombok.Data;

/**
 * http://fex.baidu.com/ueditor/#dev-request_specification
 * UEditor 请求格式规范 uploadimage
 * {
 *     "state": "SUCCESS",
 *     "url": "upload/demo.jpg",
 *     "title": "demo.jpg",
 *     "original": "demo.jpg"
 * }
 * @author wande
 * @version 1.0
 * @date 2020/07/22
 */
@Data
@Builder
public class UeDitorUpLoadImageDto {

    private String state;
    private String url;
    private String title;
    private String original;

}

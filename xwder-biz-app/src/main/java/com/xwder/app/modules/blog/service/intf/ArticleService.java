package com.xwder.app.modules.blog.service.intf;

import com.xwder.app.modules.blog.dto.UeDitorUploadConfigDto;

/**
 * 文章service
 *
 * @author wande
 * @version 1.0
 * @date 2020/07/22
 */
public interface ArticleService {

    /**
     * UEditor 请求格式规范 config
     * @return
     */
    UeDitorUploadConfigDto getUeDitorUploadConfigDto();

}

package com.xwder.app.modules.blog.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.xwder.app.modules.blog.service.intf.ArticleService;
import com.xwder.app.sysmodules.file.dto.UEditorUploadConfigDto;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 文章service impl
 *
 * @author wande
 * @version 1.0
 * @date 2020/07/22
 */
@Service
public class ArticleServiceImpl implements ArticleService {

    /**
     * UEditor 请求格式规范 config
     *
     * @return
     */
    @Override
    public Map getUeDitorUploadConfig() {
        UEditorUploadConfigDto ueDitorUploadConfigDto = new UEditorUploadConfigDto();
        Map<String, Object> configMap = BeanUtil.beanToMap(ueDitorUploadConfigDto);
        return configMap;
    }


}

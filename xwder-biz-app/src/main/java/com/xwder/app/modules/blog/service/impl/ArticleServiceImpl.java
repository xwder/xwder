package com.xwder.app.modules.blog.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.xwder.app.modules.blog.service.intf.ArticleService;
import com.xwder.app.sysmodules.file.dto.UeDitorUploadConfigDto;
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
        UeDitorUploadConfigDto ueDitorUploadConfigDto = new UeDitorUploadConfigDto();
        ueDitorUploadConfigDto.setImageUrl("/setImageUrl");
        ueDitorUploadConfigDto.setImagePath("/path");
        ueDitorUploadConfigDto.setImagePath("/setImagePath");
        ueDitorUploadConfigDto.setImageMaxSize(5000);
        ueDitorUploadConfigDto.setImageFieldName("file");

        Map<String, Object> configMap = BeanUtil.beanToMap(ueDitorUploadConfigDto);
        configMap.put("imageActionName","uploadimage");
        configMap.put("imageUrlPrefix","");
        return configMap;
    }


}

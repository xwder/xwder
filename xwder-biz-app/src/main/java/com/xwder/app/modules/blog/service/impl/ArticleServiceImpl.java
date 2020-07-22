package com.xwder.app.modules.blog.service.impl;

import com.xwder.app.modules.blog.dto.UeDitorUploadConfigDto;
import com.xwder.app.modules.blog.repository.ArticleRepository;
import com.xwder.app.modules.blog.service.intf.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 文章service impl
 *
 * @author wande
 * @version 1.0
 * @date 2020/07/22
 */
@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    /**
     * UEditor 请求格式规范 config
     *
     * @return
     */
    @Override
    public UeDitorUploadConfigDto getUeDitorUploadConfigDto() {
        UeDitorUploadConfigDto ueDitorUploadConfigDto = new UeDitorUploadConfigDto();
        ueDitorUploadConfigDto.setImageUrl("imageUrl");
        ueDitorUploadConfigDto.setImagePath("/ueditor");
        ueDitorUploadConfigDto.setImageMaxSize(5000);
        ueDitorUploadConfigDto.setImageFieldName("upfile");
        return ueDitorUploadConfigDto;
    }


}

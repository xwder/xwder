package com.xwder.app.sysmodules.file.controller;

import cn.hutool.core.bean.BeanUtil;
import com.xwder.app.modules.blog.service.intf.ArticleService;
import com.xwder.app.sysmodules.file.dto.UEditorUploadConfigDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * ue 编辑器配置config
 *
 * @author xwder
 * @Description:
 * @date 2020/7/22 23:58
 */
@RequestMapping(value = "/ueditor")
@Controller
public class UeDitorController {


    /**
     * ueditor 配置 和 内部静态静态资源地址配置 和 对应的 static/ueditor 请求对应
     *
     * @param request
     * @param action
     * @return
     */
    @RequestMapping("")
    @ResponseBody
    public Map getUeDitorUploadConfigDto(HttpServletRequest request, String action) {
        UEditorUploadConfigDto ueDitorUploadConfigDto = new UEditorUploadConfigDto();
        Map<String, Object> configMap = BeanUtil.beanToMap(ueDitorUploadConfigDto);
        return configMap;
    }
}

package com.xwder.app.modules.blog.controller;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.xwder.app.modules.blog.service.intf.ArticleService;
import com.xwder.app.utils.SessionUtil;
import com.xwder.cloud.commmon.api.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wande
 * @version 1.0
 * @date 2020/07/22
 */
@RequestMapping(value = "/blog/article")
@Controller
public class ArticleController {

    @Autowired
    private ArticleService articleService;


    @RequestMapping("/edit")
    public String articleEdit() {
        return "blog/article/edit";
    }

    @ResponseBody
    @PostMapping(value = "/doPreview")
    public Object articlePreview(HttpServletRequest request) {
        String paramData = request.getParameter("paramData");
        JSONObject jsonObject = JSONUtil.parseObj(paramData);
        String title = (String) jsonObject.get("title");
        String summary = (String) jsonObject.get("summary");
        String content = (String) jsonObject.get("content");
        String type = (String) jsonObject.get("type");
        String category = (String) jsonObject.get("category");

        JSONArray jsonArray = (JSONArray) jsonObject.get("tags");
        List tags = jsonArray.toList(Integer.class);


        Map previewMap = new HashMap<>();
        previewMap.put("title",title);
        previewMap.put("summary",summary);
        previewMap.put("title",title);
        previewMap.put("title",title);
        SessionUtil.setSessionAttribute("previewMap",previewMap);


        return CommonResult.success();
    }
}

package com.xwder.app.modules.blog.controller;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.xwder.app.modules.blog.entity.Category;
import com.xwder.app.modules.blog.service.intf.ArticleService;
import com.xwder.app.modules.blog.service.intf.CategoryService;
import com.xwder.app.modules.blog.service.intf.TagService;
import com.xwder.app.utils.SessionUtil;
import com.xwder.cloud.commmon.api.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private TagService tagService;


    @RequestMapping("/edit")
    public String articleEdit() {
        return "blog/article/edit";
    }

    @RequestMapping(value = "/preview")
    public String articlePreview(Model model){
        return "blog/article/proview";
    }

    @ResponseBody
    @PostMapping(value = "/doPreview")
    public Object toArticlePreview(HttpServletRequest request) {
        String paramData = request.getParameter("paramData");
        JSONObject jsonObject = JSONUtil.parseObj(paramData);
        String title = (String) jsonObject.get("title");
        String summary = (String) jsonObject.get("summary");
        String content = (String) jsonObject.get("content");
        String type = (String) jsonObject.get("type");

        String categoryId = (String) jsonObject.get("category");
        Category category = categoryService.getCategoryById(Long.parseLong(categoryId));

        JSONArray jsonArray = (JSONArray) jsonObject.get("tags");
        List tags = jsonArray.toList(Integer.class);
        List tagList = tagService.listTagById(tags);

        Map previewMap = new HashMap<>();
        previewMap.put("title",title);
        previewMap.put("summary",summary);
        previewMap.put("content",content);
        previewMap.put("type",type);
        previewMap.put("category",category);
        previewMap.put("tagList",tagList);
        SessionUtil.setSessionAttribute("previewMap",previewMap);


        return CommonResult.success();
    }
}

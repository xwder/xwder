package com.xwder.app.modules.blog.controller;

import com.xwder.app.modules.blog.service.intf.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
}

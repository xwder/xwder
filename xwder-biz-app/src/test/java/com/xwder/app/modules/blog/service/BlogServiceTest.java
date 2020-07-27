package com.xwder.app.modules.blog.service;

import com.xwder.app.XwderApplication;
import com.xwder.app.modules.blog.entity.Article;
import com.xwder.app.modules.blog.service.intf.ArticleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author wande
 * @version 1.0
 * @date 2020/07/27
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = XwderApplication.class)
public class BlogServiceTest {

    @Autowired
    private ArticleService articleService;

    @Test
    public void listArticleByUserIdTest() {
        Page<Article> articlePage = articleService.listArticleByUserId(1L, 1L, 1, 1);
        System.out.println(articlePage.toString());

    }
}

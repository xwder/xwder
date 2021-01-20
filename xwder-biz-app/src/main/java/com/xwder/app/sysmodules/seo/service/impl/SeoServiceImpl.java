package com.xwder.app.sysmodules.seo.service.impl;

import com.redfin.sitemapgenerator.WebSitemapGenerator;
import com.redfin.sitemapgenerator.WebSitemapUrl;
import com.xwder.app.consts.SysConfigConstants;
import com.xwder.app.modules.blog.entity.Category;
import com.xwder.app.modules.blog.entity.Tag;
import com.xwder.app.modules.blog.service.intf.ArticleService;
import com.xwder.app.modules.blog.service.intf.CategoryService;
import com.xwder.app.modules.blog.service.intf.TagService;
import com.xwder.app.sysmodules.seo.service.intf.SeoService;
import com.xwder.app.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 网站seo
 *
 * @author xwder
 * @date 2021/1/20 9:48
 */
@Slf4j
@Service
public class SeoServiceImpl implements SeoService {

    @Autowired
    private TagService tagService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ArticleService articleService;

    /**
     * 网站siteMap
     *
     * @return
     */
    @Override
    public String createSiteMapXmlContent(String domain) {
        WebSitemapGenerator wsg = null;
        try {
            wsg = new WebSitemapGenerator(domain);
            // 首页 url
            WebSitemapUrl indexUrl = new WebSitemapUrl.Options(domain + "index.html").build();
            wsg.addUrl(indexUrl);
            // 登录注册页面
            WebSitemapUrl webSitemapUrl = new WebSitemapUrl.Options(domain + "login.html").build();
            wsg.addUrl(webSitemapUrl);
            webSitemapUrl = new WebSitemapUrl.Options(domain + "register.html").build();
            wsg.addUrl(webSitemapUrl);

            // 分类和标签 访问页面
            String blogListUrl = domain + "blog/article/list.html";
            WebSitemapUrl blogListWebSitemapUrl = new WebSitemapUrl.Options(blogListUrl).build();
            // 获取所有的分类和标签列表
            List<Tag> tags = tagService.listTagByUserId(SysConfigConstants.adminUserId);
            List categoryList = categoryService.listCategoryByUserId(SysConfigConstants.adminUserId);
            for (Object o : categoryList) {
                Category category = (Category) o;
                WebSitemapUrl tmpUrl = new WebSitemapUrl.Options(blogListUrl + "?categoryId=" + category.getId() + "&categoryName=" + category.getCategoryName()).build();
                wsg.addUrl(tmpUrl);
            }
            for (Tag tag : tags) {
                WebSitemapUrl tmpUrl = new WebSitemapUrl.Options(blogListUrl + "?tagId=" + tag.getId() + "&tagName=" + tag.getTagName()).build();
                wsg.addUrl(tmpUrl);
            }
            // 博客文章url
            List<Map> articleList = articleService.listArticleIdAndArticleUpdateTimeBOrderByUserId(SysConfigConstants.adminUserId);
            for (Map articleMap : articleList) {
                WebSitemapUrl tmpUrl = new WebSitemapUrl.Options(String.format("%sblog/article/%s.html", domain, articleMap.get("id")))
                        .lastMod(DateUtil.formatDate((Date) articleMap.get("gmt_modified"), DateUtil.format_yyyy_MM_dd)).build();
                wsg.addUrl(tmpUrl);
            }
        } catch (Exception e) {
            log.error("create sitemap xml error:", e);
        }
        return String.join("", wsg.writeAsStrings());
    }
}

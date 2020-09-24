package com.xwder.app.config.web;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xwder.app.attribute.SysConfigAttribute;
import com.xwder.app.modules.blog.service.intf.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

/**
 * 全局数据启动时加载
 *
 * @author wande
 * @version 1.0
 * @date 2020/09/22
 */
@Slf4j
@Component
public class GlobalDataCacheConfig implements CommandLineRunner {

    /**
     * 首页数据缓存  博客分类信息等
     * potalDataMap 包含 ->博客归档分类信息
     */
    public static Map portalDataMap = Maps.newHashMap();

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SysConfigAttribute sysConfigAttribute;

    /**
     * 初始化首页 博客分类等缓存信息
     */
    public void initPortalData() {
        // 博客归档分类信息
        List<Map> categoryMapList = categoryService.listCategoryArticleCount(null);
        portalDataMap.put("categoryMapList", categoryMapList);
    }

    @Override
    public void run(String... args) throws Exception {
        initPortalData();
    }
}

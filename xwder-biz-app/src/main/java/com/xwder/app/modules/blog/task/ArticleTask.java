package com.xwder.app.modules.blog.task;

import com.xwder.app.consts.RedisConstant;
import com.xwder.app.modules.blog.service.intf.ArticleService;
import com.xwder.app.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 博客文章redis数据同步至数据库
 *
 * @author wande
 * @version 1.0
 * @date 2020/07/31
 */
@Component(value = "articleDataSyncTask")
public class ArticleTask {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private ArticleService articleService;

    /**
     * 同步逻辑：
     * 取redis博客文章redis 通配key
     * 更新article浏览量、
     */
    public void articleDataSyncService() {
        // 通配 key
        String articleReadCountRedisKey = RedisConstant.BLOG_ARTICLE_READCOUNT + ":*";
        List<String> list = redisUtil.getKeyList(articleReadCountRedisKey);
        for (String key : list) {
            Integer count = (Integer) redisUtil.get(key);
            String[] splits = key.split(":");
            Long id = Long.parseLong(splits[splits.length-1]);
            articleService.updateArticleReadCount(id,count.longValue());
        }
        System.out.println(list);
    }

}

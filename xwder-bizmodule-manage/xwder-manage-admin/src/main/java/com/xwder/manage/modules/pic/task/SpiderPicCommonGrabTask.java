package com.xwder.manage.modules.pic.task;

import com.xwder.manage.utils.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * xwder-spider 图片类定时任务爬取
 *
 * @author wande
 * @version 1.0
 * @date 2020/03/02
 */
@Slf4j
@Component("spiderPicCommonGrabTask")
public class SpiderPicCommonGrabTask {

    /**
     * xwder-spider 爬虫服务抓取url COMMON_SPIDER_GRAB_TASK
     *
     * @param url
     */
    public void spiderPicCommonGrabService(String url) {
        try {
            long startTime = System.currentTimeMillis();
            log.info("通用图片类定时爬取任务开始，url:[{}]", url);
            String result = HttpClientUtil.doPostWithOverTime(url, new HashMap<>(), 300);
            log.info("通用图片类定时爬取任务结束，url:[{}], 响应内容 [{}], 耗时 [{}]", url, result, System.currentTimeMillis() - startTime);
        } catch (Exception e) {
            log.error("通用图片类定时爬取任务失败，url:[{}], 错误消息 [{}]", url, e);
        }
    }
}

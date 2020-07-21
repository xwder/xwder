package com.xwder.app.sysmodules.quartz.task;

import com.xwder.app.utils.DateUtil;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 定时任务调度测试
 *
 * @author xwder
 */
@Component("ryTask")
public class RyTask {
    public void ryMultipleParams(String s, Boolean b, Long l, Double d, Integer i) {
        System.out.println(DateUtil.formatDate(new Date(), DateUtil.format_yyyy_MM_dd_HHmmss) +
                " 执行多参方法： 字符串类型{" + s + "}，布尔类型{" + b.toString() +
                "}，长整型{" + l.toString() + "}，浮点型{" + d.toString() + "}，整形{" + i.toString() + "}");
    }

    public void ryParams(String params) {
        System.out.println(DateUtil.formatDate(new Date(), DateUtil.format_yyyy_MM_dd_HHmmss) + " 执行有参方法：" + params);
    }

    public void ryNoParams() {
        System.out.println(DateUtil.formatDate(new Date(), DateUtil.format_yyyy_MM_dd_HHmmss) + " 执行无参方法");
    }
}

package com.xwder.app.sysmodules.quartz.task;

import com.xwder.app.utils.DateUtil;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author wande
 * @version 1.0
 * @date 2020/07/21
 */
@Component("taskTest")
public class TaskTest {

    public void testNoParams() {
        System.out.println(DateUtil.formatDate(new Date(), DateUtil.format_yyyy_MM_dd_HHmmss) + " 执行无参方法 testNoParams");
    }
}

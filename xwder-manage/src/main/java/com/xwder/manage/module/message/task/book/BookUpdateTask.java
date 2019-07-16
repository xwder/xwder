package com.xwder.manage.module.message.task.book;

import com.xwder.manage.module.message.service.BookUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Author: xwder
 * @Date: 2019/7/16 20:33
 * @Description:
 */
@Component
public class BookUpdateTask {

    @Autowired
    private BookUpdateService bookUpdateService;


    @Scheduled(fixedDelay = 1000 * 300)
    public void bookUpdateSendMail() {
        System.out.println(new Date());
        bookUpdateService.sendBooUpdateMessageWithMailAndBooInfo("烽火戏诸侯", "剑来", "1123511540@qq.com", "小说更新");
    }


}

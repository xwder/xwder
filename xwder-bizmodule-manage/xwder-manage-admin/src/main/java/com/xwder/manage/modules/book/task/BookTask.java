package com.xwder.manage.modules.book.task;

import com.xwder.manage.modules.book.service.intf.IChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author wande
 * @version 1.0
 * @date 2020/01/08
 */
@Component("bookChapterUpdateTask")
public class BookTask {

    @Autowired
    private IChapterService iChapterService;

    public void bookUpdateNotice(String author, String bookName, String bookUrl) {
        iChapterService.bookUpdateNotice(author, bookName, bookUrl);
    }

}

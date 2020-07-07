package com.xwder.biz.app.modules.novel.service.intf;

import com.xwder.cloud.commmon.api.CommonResult;

/**
 * 书籍模块 service
 */
public interface BookInfoService {

    /**
     * 根据书籍名称下载txt
     *
     * @param bookName
     * @return
     */
    CommonResult downBookByBookName(String bookName);

}

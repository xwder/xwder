package com.xwder.manage.web.modules.book.service.intf;

import com.xwder.manage.common.core.page.TableDataInfo;
import com.xwder.manage.web.modules.book.dto.BookInfoDto;

/**
 * bookService
 *
 * @author xwder
 * @date 2019/12/30
 */
public interface IBookService {

    /**
     * @param PageNum
     * @param pageSize
     * @param bookInfoDto
     * @return
     */
    TableDataInfo listBookInfo(int PageNum, int pageSize, BookInfoDto bookInfoDto);
}

package com.xwder.manage.modules.book.service.intf;

import com.xwder.manage.common.core.page.TableDataInfo;
import com.xwder.manage.modules.book.dto.BookChapterDto;
import com.xwder.manage.modules.book.dto.BookInfoDto;

import java.util.List;

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

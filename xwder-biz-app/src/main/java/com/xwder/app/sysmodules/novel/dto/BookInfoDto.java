package com.xwder.app.sysmodules.novel.dto;

import com.xwder.app.common.jpa.annotation.QueryField;
import com.xwder.app.common.jpa.domain.CommonPageQuery;
import com.xwder.app.common.jpa.enumobj.QueryType;
import lombok.Data;

/**
 * @author wande
 * @version 1.0
 * @date 2020/09/11
 */
@Data
public class BookInfoDto extends CommonPageQuery.DefaultCommonPageQuery {

    @QueryField(type = QueryType.FULL_LIKE,name = "bookName")
    private String bookName;

}

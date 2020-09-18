package com.xwder.app.sysmodules.blog.dto;

import com.xwder.app.common.jpa.annotation.QueryField;
import com.xwder.app.common.jpa.domain.CommonPageQuery;
import com.xwder.app.common.jpa.enumobj.QueryType;

import javax.validation.constraints.NotBlank;

/**
 * @author wande
 * @version 1.0
 * @date 2020/09/18
 */
public class CategoryDto extends CommonPageQuery.DefaultCommonPageQuery {

    @QueryField(type = QueryType.FULL_LIKE, name = "categoryName")
    private String categoryName;

    @NotBlank
    @QueryField(type = QueryType.EQUAL, name = "userId")
    private String userId;
}

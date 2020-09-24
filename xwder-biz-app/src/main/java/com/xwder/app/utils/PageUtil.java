package com.xwder.app.utils;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * page工具类
 *
 * @author wande
 * @version 1.0
 * @date 2020/09/21
 */
public class PageUtil {

    /**
     * 构建分页对象
     *
     * @param list
     * @param pageable
     * @param total
     * @return
     */
    public static Page page(List list, Pageable pageable, long total) {
        Page page = new PageImpl<>(list, pageable, total);
        return page;
    }
}

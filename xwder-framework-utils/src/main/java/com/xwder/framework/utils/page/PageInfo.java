package com.xwder.framework.utils.page;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: xwder
 * @Date: 2019/7/12 20:16
 * @Description:
 */
@Data
@Builder
public class PageInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 当前记录起始索引
     */
    private Integer pageNum;
    /**
     * 每页显示记录数
     */
    private Integer pageSize;
    /**
     * 排序列
     */
    private String orderByColumn;
    /**
     * 总记录数
     */
    private long total;
    /**
     * 列表数据
     */
    private List<?> data;


    private Integer totalPages;
}


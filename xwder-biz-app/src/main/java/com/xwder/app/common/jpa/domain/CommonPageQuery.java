package com.xwder.app.common.jpa.domain;

import java.util.List;

/**
 * 通用分类查询接口
 *
 * @author xwder
 * @date 2020年9月14日
 */
public interface CommonPageQuery extends PageableDataQuery {
    /**
     * @return
     */
    List<SortableDataQuery.DefaultSortableDataQuery> getSort();

    /**
     * @param defaultSortableDataQueries
     */
    void setSort(List<SortableDataQuery.DefaultSortableDataQuery> defaultSortableDataQueries);

    class DefaultCommonPageQuery extends DefaultPageableDataQuery implements CommonPageQuery {
        private List<SortableDataQuery.DefaultSortableDataQuery> sort;

        public DefaultCommonPageQuery() {
        }

        @Override
        public List<SortableDataQuery.DefaultSortableDataQuery> getSort() {
            return this.sort;
        }

        @Override
        public void setSort(List<SortableDataQuery.DefaultSortableDataQuery> sort) {
            this.sort = sort;
        }
    }
}
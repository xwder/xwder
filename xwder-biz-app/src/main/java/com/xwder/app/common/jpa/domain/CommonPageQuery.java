package com.xwder.app.common.jpa.domain;

import java.util.List;

public interface CommonPageQuery extends PageableDataQuery {
    List<SortableDataQuery.DefaultSortableDataQuery> getSort();

    void setSort(List<SortableDataQuery.DefaultSortableDataQuery> defaultSortableDataQueries);

    public static class DefaultCommonPageQuery extends DefaultPageableDataQuery implements CommonPageQuery {
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
package com.xwder.app.common.jpa.domain;

public interface PageableDataQuery extends DataQuery {
    Integer DEFAULT_PAGE = 0;
    Integer DEFAULT_SIZE = 10;

    static PageableDataQuery of() {
        return of(DEFAULT_PAGE, DEFAULT_SIZE);
    }

    static PageableDataQuery of(int page, int limit) {
        return new PageableDataQuery.DefaultPageableDataQuery(page, limit);
    }

    Integer getPage();

    Integer getLimit();

    public static class DefaultPageableDataQuery implements PageableDataQuery {
        private Integer page;
        private Integer limit;

        public DefaultPageableDataQuery() {
            this.page = DEFAULT_PAGE;
            this.limit = DEFAULT_SIZE;
        }

        public DefaultPageableDataQuery(Integer page, Integer limit) {
            this.page = DEFAULT_PAGE;
            this.limit = DEFAULT_SIZE;
            this.page = page;
            this.limit = limit;
        }

        @Override
        public Integer getPage() {
            return this.page;
        }

        @Override
        public Integer getLimit() {
            return this.limit;
        }

        public void setPage(Integer page) {
            this.page = page;
        }

        public void setLimit(Integer limit) {
            this.limit = limit;
        }
    }
}
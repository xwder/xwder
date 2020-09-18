package com.xwder.app.common.jpa.domain;

/**
 * 分页和排序
 *
 * @author xwder
 * @date 2020年9月14日
 */
public interface PageableDataQuery extends DataQuery {
    Integer DEFAULT_PAGE = 0;
    Integer DEFAULT_SIZE = 10;

    /**
     * 返回默认页码 和 页数量
     *
     * @return PageableDataQuery
     */
    static PageableDataQuery of() {
        return of(DEFAULT_PAGE, DEFAULT_SIZE);
    }

    /**
     * 返回指定的 页码和数量
     *
     * @param page  页码
     * @param limit 每页大小
     * @return PageableDataQuery
     */
    static PageableDataQuery of(int page, int limit) {
        return new PageableDataQuery.DefaultPageableDataQuery(page, limit);
    }

    /**
     * page
     *
     * @return page
     */
    Integer getPage();

    /**
     * size
     *
     * @return limit
     */
    Integer getLimit();

    class DefaultPageableDataQuery implements PageableDataQuery {
        private Integer page;
        private Integer limit;

        public DefaultPageableDataQuery() {
            this.page = DEFAULT_PAGE;
            this.limit = DEFAULT_SIZE;
        }

        public DefaultPageableDataQuery(Integer page, Integer limit) {
            if (page >= 1) {
                page = page - 1;
            }
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
            if (page >= 1) {
                page = page - 1;
            }
            this.page = page;
        }

        public void setLimit(Integer limit) {
            this.limit = limit;
        }
    }
}
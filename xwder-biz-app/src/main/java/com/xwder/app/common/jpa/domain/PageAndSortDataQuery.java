package com.xwder.app.common.jpa.domain;

/**
 * 数据分页排序查询接口
 * @author xwder
 * @date 2020年9月14日
 */
public interface PageAndSortDataQuery extends PageableDataQuery, SortableDataQuery {
    class DefaultPageAndSortDataQuery extends DefaultPageableDataQuery implements PageAndSortDataQuery {
        private String order;
        private String direction = "ASC";

        public DefaultPageAndSortDataQuery() {
        }

        @Override
        public String getOrder() {
            return this.order;
        }

        @Override
        public String getDirection() {
            return this.direction;
        }

        public void setOrder(String order) {
            this.order = order;
        }

        public void setDirection(String direction) {
            this.direction = direction;
        }
    }
}

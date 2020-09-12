package com.xwder.app.common.jpa.domain;

public interface PageAndSortDataQuery extends PageableDataQuery, SortableDataQuery {
    public static class DefaultPageAndSortDataQuery extends DefaultPageableDataQuery implements PageAndSortDataQuery {
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

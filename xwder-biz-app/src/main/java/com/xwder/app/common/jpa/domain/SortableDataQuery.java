package com.xwder.app.common.jpa.domain;

public interface SortableDataQuery extends DataQuery {
    String DEFAULT_DIRECTION = "ASC";
    String NORMAL_DIRECTION = "NORMAL";

    static SortableDataQuery of(String order) {
        return of(order, "ASC");
    }

    static SortableDataQuery of(String order, String direction) {
        return new SortableDataQuery.DefaultSortableDataQuery(order, direction);
    }

    String getOrder();

    String getDirection();

    public static class DefaultSortableDataQuery implements SortableDataQuery {
        private String order;
        private String direction = "ASC";

        public DefaultSortableDataQuery() {
        }

        public DefaultSortableDataQuery(String order, String direction) {
            this.order = order;
            this.direction = direction;
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
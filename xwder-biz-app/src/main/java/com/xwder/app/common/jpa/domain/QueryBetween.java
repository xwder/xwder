package com.xwder.app.common.jpa.domain;

public class QueryBetween<T extends Comparable<?>> {
    private T before;
    private T after;

    public QueryBetween() {
    }

    public QueryBetween(T before, T after) {
        this.before = before;
        this.after = after;
    }

    public T getBefore() {
        return this.before;
    }

    public void setBefore(T before) {
        this.before = before;
    }

    public T getAfter() {
        return this.after;
    }

    public void setAfter(T after) {
        this.after = after;
    }
}
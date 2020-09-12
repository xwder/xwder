package com.xwder.app.common.jpa.enumobj;

public enum QueryType {
    EQUAL,
    BETWEEN,
    LESS_THAN,
    LESS_THAN_EQUAL,
    GREATER_THAN,
    GREATER_THAN_EQUAL,
    NOT_EQUAL,
    IS_NULL,
    IS_NOT_NULL,
    RIGHT_LIKE,
    LEFT_LIKE,
    FULL_LIKE,
    DEFAULT_LIKE,
    NOT_LIKE,
    IN;

    private QueryType() {
    }
}
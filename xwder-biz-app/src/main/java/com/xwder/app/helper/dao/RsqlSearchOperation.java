package com.xwder.app.helper.dao;

import cz.jirutka.rsql.parser.ast.ComparisonOperator;
import cz.jirutka.rsql.parser.ast.RSQLOperators;


/**
 * RSql操作符枚举
 *
 * @author wande
 * @version 1.0
 * @date 2020/08/05
 */
public enum RsqlSearchOperation {
    EQUAL(RSQLOperators.EQUAL),
    NOT_EQUAL(RSQLOperators.NOT_EQUAL),
    GREATER_THAN(RSQLOperators.GREATER_THAN),
    GREATER_THAN_OR_EQUAL(RSQLOperators.GREATER_THAN_OR_EQUAL),
    LESS_THAN(RSQLOperators.LESS_THAN),
    LESS_THAN_OR_EQUAL(RSQLOperators.LESS_THAN_OR_EQUAL),
    IN(RSQLOperators.IN),
    NOT_IN(RSQLOperators.NOT_IN);

    private ComparisonOperator operator;

    private RsqlSearchOperation(ComparisonOperator operator) {
        this.operator = operator;
    }

    public static RsqlSearchOperation getSimpleOperator(ComparisonOperator operator) {
        for (RsqlSearchOperation operation : values()) {
            if (operation.operator == operator) {
                return operation;
            }
        }
        return null;
    }

}

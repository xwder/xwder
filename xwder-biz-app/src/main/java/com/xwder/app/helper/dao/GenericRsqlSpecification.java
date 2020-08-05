package com.xwder.app.helper.dao;

import com.xwder.app.utils.DateUtil;
import cz.jirutka.rsql.parser.ast.ComparisonOperator;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * 自定义Specification
 *
 * @author wande
 * @version 1.0
 * @date 2020/08/05
 */
public class GenericRsqlSpecification<T> implements Specification<T> {
    private String property;
    private ComparisonOperator operator;
    private List<String> arguments;

    public GenericRsqlSpecification(String property, ComparisonOperator operator, List<String> arguments) {
        super();
        this.property = property;
        this.operator = operator;
        this.arguments = arguments;
    }

    @Override
    public Predicate toPredicate(
            Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

        List<Object> args = castArguments(root);
        Object argument = args.get(0);
        switch (RsqlSearchOperation.getSimpleOperator(operator)) {

            case EQUAL: {
                if (argument instanceof String) {
                    if (argument.toString().indexOf('*') > -1) {
                        return builder.like(
                                root.<String>get(property), argument.toString().replace('*', '%'));
                    } else {
                        return builder.equal(
                                root.<String>get(property), argument.toString());
                    }
                } else if (argument == null) {
                    return builder.isNull(root.get(property));
                } else {
                    return builder.equal(root.get(property), argument);
                }
            }
            case NOT_EQUAL: {
                if (argument instanceof String) {
                    return builder.notLike(
                            root.<String>get(property), argument.toString().replace('*', '%'));
                } else if (argument == null) {
                    return builder.isNotNull(root.get(property));
                } else {
                    return builder.notEqual(root.get(property), argument);
                }
            }
            case GREATER_THAN: {
                if (argument instanceof Date) {
                    return builder.lessThan(root.<Date>get(property), (Date) argument);
                } else {
                    return builder.greaterThan(root.<String>get(property), argument.toString());
                }
            }
            case GREATER_THAN_OR_EQUAL: {
                if (argument instanceof Date) {
                    return builder.greaterThanOrEqualTo(root.<Date>get(property), (Date) argument);
                } else {
                    return builder.greaterThanOrEqualTo(
                            root.<String>get(property), argument.toString());
                }
            }
            case LESS_THAN: {
                if (argument instanceof Date) {
                    return builder.lessThan(root.<Date>get(property), (Date) argument);
                } else {
                    return builder.lessThan(root.<String>get(property), argument.toString());
                }
            }
            case LESS_THAN_OR_EQUAL: {
                if (argument instanceof Date) {
                    return builder.lessThanOrEqualTo(root.<Date>get(property), (Date) argument);
                } else {
                    return builder.lessThanOrEqualTo(
                            root.<String>get(property), argument.toString());
                }
            }
            case IN:
                return root.get(property).in(args);
            case NOT_IN:
                return builder.not(root.get(property).in(args));
            default:
                return null;
        }


    }

    private List<Object> castArguments(Root<T> root) {
        List<Object> args = new ArrayList<Object>();
        Class<? extends Object> type = root.get(property).getJavaType();

        for (String argument : arguments) {
            if (type.equals(Integer.class)) {
                args.add(Integer.parseInt(argument));
            } else if (type.equals(Long.class)) {
                args.add(Long.parseLong(argument));
            } else if (type.equals(Date.class)) {
                args.add(DateUtil.parseDate(argument));
            } else if (type.isEnum()) {
                args.add(Enum.valueOf(type.asSubclass(Enum.class), argument));
            } else {
                if (argument.equals("nil")) {
                    args.add(null);
                } else {
                    args.add(argument);
                }
            }
        }

        return args;
    }
}
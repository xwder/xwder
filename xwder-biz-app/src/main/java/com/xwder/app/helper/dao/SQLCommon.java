package com.xwder.app.helper.dao;


import com.xwder.app.utils.StringUtils;
import cz.jirutka.rsql.parser.RSQLParser;
import cz.jirutka.rsql.parser.ast.Node;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.Query;

/**
 * @author wande
 * @version 1.0
 * @date 2020/08/05
 */
class SQLCommon {
    static void setPageAdapter(Query query, Integer... splitPage) {
        if (ArrayUtils.isNotEmpty(splitPage) && splitPage.length == 2) {
            Integer start = splitPage[0];
            Integer limit = splitPage[1];

            if (start != null && limit != null) {
                query.setFirstResult(start);
                query.setMaxResults(limit);
            }
        }
    }

    /**
     * 通过Pageable计算起始记录行号
     *
     * @param pageable
     * @return
     */
    static int calcStartFromPageable(Pageable pageable) {
        return pageable.getPageNumber() * pageable.getPageSize();
    }

    /**
     * 通过pageable计算截止记录行号
     *
     * @param pageable
     * @return
     */
    static int calcLimitFromPageable(Pageable pageable) {
        return pageable.getPageSize();
    }

    /**
     * search添加逻辑删除条件
     *
     * @param search
     * @return
     */
    static String searchAddIsVoid(String search) {
        //默认没有加isVoid条件
        if (StringUtils.isNotEmpty(search)) {
            if (search.indexOf("isVoid") < 0) {
                search = search + ";isVoid==0";
            }
        } else {
            search = "isVoid==0";
        }
        return search;
    }

    /**
     * 将search转换为Spec对象
     *
     * @param search
     * @param <T>
     * @return
     */
    public static <T> Specification<T> searchToSpec(String search) {
        Specification<T> spec = null;
        search = SQLCommon.searchAddIsVoid(search);

        if (StringUtils.isNotEmpty(search)) {
            Node rootNode = new RSQLParser().parse(search);
            spec = rootNode.accept(new CustomRsqlVisitor<T>());
        }
        return spec;
    }
}

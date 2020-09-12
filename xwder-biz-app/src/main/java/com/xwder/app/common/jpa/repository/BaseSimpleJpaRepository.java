
package com.xwder.app.common.jpa.repository;


import com.xwder.app.common.jpa.annotation.QueryField;
import com.xwder.app.common.jpa.domain.*;
import com.xwder.app.common.jpa.enumobj.QueryType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import javax.persistence.criteria.CriteriaBuilder.In;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@NoRepositoryBean
public class BaseSimpleJpaRepository<T, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements BaseJpaRepository<T, ID>, JpaSpecificationExecutor<T> {
    private static Logger logger = LoggerFactory.getLogger(BaseSimpleJpaRepository.class);
    private Class domainClass;

    public BaseSimpleJpaRepository(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.domainClass = entityInformation.getJavaType();
    }

    public BaseSimpleJpaRepository(Class<T> domainClass, EntityManager em) {
        super(domainClass, em);
        this.domainClass = domainClass;
    }

    @Override
    public List<T> findAll(DataQuery dataQuery) {
        return this.findAll((Specification) (new BaseSimpleJpaRepository.BaseSimpleJpaRepositorySpecification(dataQuery)));
    }

    @Override
    public List<T> findAll(DataQuery dataQuery, Sort sort) {
        return this.findAll((Specification) (new BaseSimpleJpaRepository.BaseSimpleJpaRepositorySpecification(dataQuery)), (Sort) sort);
    }

    @Override
    public Page<T> findAll(DataQuery dataQuery, Pageable pageable) {
        return this.findAll((Specification) (new BaseSimpleJpaRepository.BaseSimpleJpaRepositorySpecification(dataQuery)), (Pageable) pageable);
    }

    @Override
    public List<T> findAll(SortableDataQuery dataQuery) {
        String order = dataQuery.getOrder();
        String direction = StringUtils.isEmpty(dataQuery.getDirection()) ? "ASC" : dataQuery.getDirection();
        if (StringUtils.isEmpty(order)) {
            return this.findAll((Specification) (new BaseSimpleJpaRepository.BaseSimpleJpaRepositorySpecification(dataQuery)));
        } else {
            Sort sort = Sort.by(Direction.fromString(direction), new String[]{order});
            return this.findAll(dataQuery, (Sort) sort);
        }
    }

    @Override
    public Page<T> findAll(PageableDataQuery dataQuery) {
        return this.findAll(dataQuery, (Pageable) PageRequest.of(dataQuery.getPage(), dataQuery.getLimit()));
    }

    @Override
    public Page<T> findAll(PageAndSortDataQuery dataQuery) {
        String order = dataQuery.getOrder();
        String direction = StringUtils.isEmpty(dataQuery.getDirection()) ? "ASC" : dataQuery.getDirection();
        Pageable pageable = StringUtils.isEmpty(order) ? PageRequest.of(dataQuery.getPage(), dataQuery.getLimit()) : PageRequest.of(dataQuery.getPage(), dataQuery.getLimit(), Sort.by(Direction.fromString(direction), new String[]{order}));
        return this.findAll(dataQuery, (Pageable) pageable);
    }

    @Override
    public Page<T> findAll(CommonPageQuery dataQuery) {
        List<SortableDataQuery.DefaultSortableDataQuery> sortableDataQueries = dataQuery.getSort();
        Pageable pageable = null;
        if (null != sortableDataQueries && sortableDataQueries.size() > 0) {
            List<Order> sorts = new ArrayList();
            sortableDataQueries.forEach((s) -> {
                if (!"NORMAL".equalsIgnoreCase(s.getDirection())) {
                    Direction direction = Direction.fromString(s.getDirection());
                    Order order = direction == Direction.ASC ? Order.asc(s.getOrder()) : Order.desc(s.getOrder());
                    sorts.add(order);
                }
            });
            pageable = PageRequest.of(dataQuery.getPage(), dataQuery.getLimit(), Sort.by(sorts));
        } else {
            pageable = PageRequest.of(dataQuery.getPage(), dataQuery.getLimit());
        }

        return this.findAll(dataQuery, (Pageable) pageable);
    }

    @Override
    public List<T> findAllWidthOutPage(CommonPageQuery dataQuery) {
        return this.findAll((Specification) (new BaseSimpleJpaRepository.BaseSimpleJpaRepositorySpecification(dataQuery)));
    }

    protected Predicate generatePredicate(DataQuery dataQuery, Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList();
        Field[] fields = dataQuery.getClass().getDeclaredFields();
        Field[] var7 = fields;
        int var8 = fields.length;

        for (int var9 = 0; var9 < var8; ++var9) {
            Field field = var7[var9];
            field.setAccessible(true);
            QueryField annotation = (QueryField) field.getAnnotation(QueryField.class);
            if (annotation != null) {
                String queryField;
                if (!StringUtils.isEmpty(annotation.name())) {
                    queryField = annotation.name();
                } else {
                    queryField = field.getName();
                }

                QueryType queryType = annotation.type();

                Object value;
                try {
                    value = field.get(dataQuery);
                } catch (IllegalAccessException var16) {
                    logger.error(String.format("Get value of field named %s for DataQuery %s error", field.getName(), dataQuery.getClass()), var16);
                    throw new RuntimeException(var16);
                }

                if (value == null) {
                    if (!annotation.nullAble()) {
                        throw new IllegalArgumentException(String.format("field %s of %s can not be null", field.getName(), dataQuery.getClass()));
                    }

                    if (queryType != QueryType.IS_NULL && queryType != QueryType.IS_NOT_NULL) {
                        continue;
                    }
                }

                predicates.add(this.generatePredicate(value, queryField, queryType, root, criteriaBuilder));
            }
        }

        if (predicates.size() == 0) {
            return criteriaBuilder.and(new Predicate[0]);
        } else {
            Predicate[] result = (Predicate[]) predicates.toArray(new Predicate[predicates.size()]);
            return criteriaBuilder.and(result);
        }
    }

    private Predicate generatePredicate(Object value, String queryField, QueryType queryType, Root<T> root, CriteriaBuilder criteriaBuilder) {
        Predicate predicate = null;
        switch (queryType) {
            case EQUAL:
                Path<Object> equal = this.getPathByQueryField(queryField, root);
                predicate = criteriaBuilder.equal(equal, value);
                break;
            case NOT_EQUAL:
                Path<Object> notEqual = this.getPathByQueryField(queryField, root);
                predicate = criteriaBuilder.notEqual(notEqual, value);
                break;
            case BETWEEN:
                Path<Comparable> between = this.getPathByQueryField(queryField, root);
                this.checkType(value, QueryBetween.class, queryType);
                QueryBetween qb = (QueryBetween) value;
                predicate = criteriaBuilder.between(between, qb.getAfter(), qb.getBefore());
                break;
            case LESS_THAN:
                Path<Comparable> lessThan = this.getPathByQueryField(queryField, root);
                this.checkType(value, Comparable.class, queryType);
                predicate = criteriaBuilder.lessThan(lessThan, (Comparable) value);
                break;
            case LESS_THAN_EQUAL:
                Path<Comparable> lessThanEqual = this.getPathByQueryField(queryField, root);
                this.checkType(value, Comparable.class, queryType);
                predicate = criteriaBuilder.lessThanOrEqualTo(lessThanEqual, (Comparable) value);
                break;
            case GREATER_THAN:
                Path<Comparable> greaterThan = this.getPathByQueryField(queryField, root);
                this.checkType(value, Comparable.class, queryType);
                predicate = criteriaBuilder.greaterThan(greaterThan, (Comparable) value);
                break;
            case GREATER_THAN_EQUAL:
                Path<Comparable> greaterThanEqual = this.getPathByQueryField(queryField, root);
                this.checkType(value, Comparable.class, queryType);
                predicate = criteriaBuilder.greaterThanOrEqualTo(greaterThanEqual, (Comparable) value);
                break;
            case IS_NULL:
                Path<Object> isNull = this.getPathByQueryField(queryField, root);
                predicate = criteriaBuilder.isNull(isNull);
                break;
            case IS_NOT_NULL:
                Path<Object> isNotNull = this.getPathByQueryField(queryField, root);
                predicate = criteriaBuilder.isNotNull(isNotNull);
                break;
            case LEFT_LIKE:
                Path<String> leftLike = this.getPathByQueryField(queryField, root);
                this.checkType(value, String.class, queryType);
                predicate = criteriaBuilder.like(leftLike, "%" + value);
                break;
            case RIGHT_LIKE:
                Path<String> rightLike = this.getPathByQueryField(queryField, root);
                this.checkType(value, String.class, queryType);
                predicate = criteriaBuilder.like(rightLike, value + "%");
                break;
            case FULL_LIKE:
                Path<String> fullLike = this.getPathByQueryField(queryField, root);
                this.checkType(value, String.class, queryType);
                predicate = criteriaBuilder.like(fullLike, "%" + value + "%");
                break;
            case DEFAULT_LIKE:
                Path<String> defaultLike = this.getPathByQueryField(queryField, root);
                this.checkType(value, String.class, queryType);
                predicate = criteriaBuilder.like(defaultLike, value.toString());
                break;
            case NOT_LIKE:
                Path<String> notLike = this.getPathByQueryField(queryField, root);
                this.checkType(value, String.class, queryType);
                predicate = criteriaBuilder.notLike(notLike, value.toString());
                break;
            case IN:
                Path<Object> in = this.getPathByQueryField(queryField, root);
                In<Object> ins = criteriaBuilder.in(in);
                if (!(value instanceof Iterable) && !(value instanceof Object[])) {
                    throw new IllegalArgumentException(String.format("%s must act on %s or Array", queryType, Iterable.class));
                }

                if (value instanceof Iterable) {
                    ((Iterable) value).forEach(ins::value);
                }

                if (value instanceof Object[]) {
                    Arrays.stream((Object[]) ((Object[]) value)).forEach(ins::value);
                }

                predicate = ins;
        }

        return (Predicate) predicate;
    }

    private void checkType(Object value, Class clazz, QueryType queryType) {
        if (!clazz.isAssignableFrom(value.getClass())) {
            throw new IllegalArgumentException(String.format("%s must act on %s", queryType, clazz));
        }
    }

    private <Y> Path<Y> getPathByQueryField(String queryField, Path<T> path) {
        return !queryField.contains(".") ? path.get(queryField) : this.getPathByQueryField(queryField.substring(queryField.indexOf(".") + 1), path.get(queryField.substring(0, queryField.indexOf("."))));
    }
/*
    public List<T> findByRsql(String search) {
        return CrudSQL.findByRsql(this.domainClass, search, (Sort)null);
    }

    public Page<T> findByRsql(String search, Pageable pageable) {
        return CrudSQL.findByRsql(this.domainClass, search, pageable);
    }

    public List<T> findByRsql(String search, Sort sort) {
        return CrudSQL.findAll(this.domainClass, search, sort);
    }

    public long findByRsqlCount(String search) {
        return CrudSQL.findByRsqlCount(this.domainClass, search);
    }

    public List<Map> findByNativeSQL(String strSql, List params) {
        Assert.notNull(strSql, "in findByNativeSql, params strSql is null");
        return this.findByNativeSQL(strSql, params, (Pageable)null);
    }

    public List<Map> findByNativeSQL(String strSql, List params, Pageable pageable) {
        Assert.notNull(strSql, "in findByNativeSql, params strSql is null");
        return NativeSQL.findByNativeSQLPageable(strSql, params, pageable);
    }

    public <Z> List<Z> findByNativeSQL(Class<Z> dtoClass, String strSql, List params) {
        Assert.notNull(strSql, "in findByNativeSql, params strSql is null");
        Assert.notNull(dtoClass, "in findByNativeSql, params dtoClass is null");
        return this.findByNativeSQL(dtoClass, strSql, params, (Pageable)null);
    }

    public <Z> List<Z> findByNativeSQL(Class<Z> dtoClass, String strSql, List params, Pageable pageable) {
        Assert.notNull(strSql, "in findByNativeSql, params strSql is null");
        Assert.notNull(dtoClass, "in findByNativeSql, params dtoClass is null");
        return NativeSQL.findByNativeSQLPageable(strSql, params, dtoClass, pageable);
    }

    public void execute(String sql) {
        NativeSQL.execute(sql);
    }

    @Transactional
    public int update(String sql, Object... params) {
        return NativeSQL.update(sql, params);
    }

    @Transactional
    public void deleteLogic(T entity) {
        try {
            Method method = entity.getClass().getDeclaredMethod("setIsVoid");
            method.invoke(entity, 1);
            CrudSQL.update(entity);
        } catch (Exception var3) {
            logger.error(var3.getMessage());
        }

    }

    @Transactional
    public void deleteLogic(List<T> list) {
        int i = 0;
        if (ListUtil.isNotEmpty(list)) {
            Iterator var2 = list.iterator();

            while(var2.hasNext()) {
                T entity = var2.next();
                this.deleteLogic(entity);
                ++i;
                if (i % 8 == 0) {
                    CrudSQL.flush();
                }
            }
        }

    }

    @Transactional
    public void deleteLogic(String ids) {
        List<T> list = CrudSQL.findAll(this.domainClass, ids);
        this.deleteLogic(list);
    }

    @Transactional
    public void deleteLogicIDList(List<String> ids) {
        List<T> list = CrudSQL.findAll(this.domainClass, ids);
        this.deleteLogic(list);
    }*/

    protected class BaseSimpleJpaRepositorySpecification implements Specification<T> {
        DataQuery dataQuery;

        BaseSimpleJpaRepositorySpecification(DataQuery dataQuery) {
            this.dataQuery = dataQuery;
        }

        @Override
        public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
            return BaseSimpleJpaRepository.this.generatePredicate(this.dataQuery, root, query, criteriaBuilder);
        }
    }
}

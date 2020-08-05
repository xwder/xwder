package com.xwder.app.helper.dao;

import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.ConcurrentReferenceHashMap;

import javax.persistence.EntityManager;

/**
 *
 * @author wande
 * @version 1.0
 * @date 2020/08/05
 */
@Repository
public class JpaContext {
    private static EntityManager em;
    private static JdbcTemplate jdbcTemplate;
    private static ConcurrentReferenceHashMap<Class<?>, SimpleJpaRepository> JPA_CACHE = new ConcurrentReferenceHashMap();

    public JpaContext(EntityManager em, JdbcTemplate jdbcTemplate) {
        JpaContext.em = em;
        JpaContext.jdbcTemplate = jdbcTemplate;
    }

    public static SimpleJpaRepository getSimpleJpaRepository(Class<?> domainClass){
        if(JPA_CACHE.containsKey(domainClass)) {
            return JPA_CACHE.get(domainClass);
        } else {
            SimpleJpaRepository jpa = new SimpleJpaRepository(domainClass, em);
            return cacheAndReturn(domainClass, jpa);
        }
    }

    public static EntityManager getEntityManager(){
        return em;
    }

    public static JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }
    /**
     * 对该实体的jpa对象进行缓存
     * @param domainClass
     * @param jpa
     * @return
     */
    private static SimpleJpaRepository cacheAndReturn(Class<?> domainClass, SimpleJpaRepository jpa) {
        JPA_CACHE.put(domainClass, jpa);
        return jpa;
    }
}

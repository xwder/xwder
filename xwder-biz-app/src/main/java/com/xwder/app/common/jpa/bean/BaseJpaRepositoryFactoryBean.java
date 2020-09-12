package com.xwder.app.common.jpa.bean;

import java.io.Serializable;
import javax.persistence.EntityManager;

import com.xwder.app.common.jpa.repository.BaseSimpleJpaRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

public class BaseJpaRepositoryFactoryBean<T extends JpaRepository<S, ID>, S, ID extends Serializable> extends JpaRepositoryFactoryBean<T, S, ID> {
    public BaseJpaRepositoryFactoryBean(Class<? extends T> repositoryInterface) {
        super(repositoryInterface);
    }

    @Override
    protected RepositoryFactorySupport createRepositoryFactory(EntityManager entityManager) {
        return new BaseJpaRepositoryFactoryBean.BaseJpaRepositoryFactory(entityManager);
    }

    private class BaseJpaRepositoryFactory extends JpaRepositoryFactory {
        BaseJpaRepositoryFactory(EntityManager entityManager) {
            super(entityManager);
        }

        @Override
        protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
            return BaseSimpleJpaRepository.class;
        }
    }
}
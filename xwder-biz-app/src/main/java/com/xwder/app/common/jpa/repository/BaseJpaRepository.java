package com.xwder.app.common.jpa.repository;

import java.io.Serializable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;

@NoRepositoryBean
@Transactional(
    readOnly = true,
    rollbackFor = {Exception.class}
)
public interface BaseJpaRepository<T, ID extends Serializable> extends DynamicQueryRepository<T, ID>, JpaRepository<T, ID>, JpaSpecificationExecutor<T> {
}

package com.xwder.cqust.repository;

import com.xwder.framework.domain.cqust.KyStudent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author: xwder
 * @Date: 2019/7/10 21:26
 * @Description:
 */
@Repository
public interface KyStudentRepository extends CrudRepository<KyStudent, Long> {

    /**
     * 分页查询
     *
     * @param pageable
     * @return Page
     */
    Page<KyStudent> findAll(Pageable pageable);
}

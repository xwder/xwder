package com.xwder.app.sysmodules.quartz.repository;

import com.xwder.app.common.jpa.repository.BaseJpaRepository;
import com.xwder.app.sysmodules.quartz.entity.SysJob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SysJobRepository extends BaseJpaRepository<SysJob, Long> {

}

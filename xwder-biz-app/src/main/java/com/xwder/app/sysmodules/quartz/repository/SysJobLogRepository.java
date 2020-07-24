package com.xwder.app.sysmodules.quartz.repository;

import com.xwder.app.sysmodules.quartz.entity.SysJobLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SysJobLogRepository extends JpaRepository<SysJobLog, Long> {

}

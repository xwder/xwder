package com.xwder.app.sysmodules.quartz.repository;

import com.xwder.app.sysmodules.quartz.entity.SysJob;
import com.xwder.app.sysmodules.quartz.entity.SysJobLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SysJobLogRepository extends JpaRepository<SysJobLog, Long> {

}

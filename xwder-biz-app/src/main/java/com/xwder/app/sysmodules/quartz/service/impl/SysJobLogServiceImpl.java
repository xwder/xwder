package com.xwder.app.sysmodules.quartz.service.impl;

import com.xwder.app.sysmodules.quartz.entity.SysJobLog;
import com.xwder.app.sysmodules.quartz.repository.SysJobLogRepository;
import com.xwder.app.sysmodules.quartz.repository.SysJobRepository;
import com.xwder.app.sysmodules.quartz.service.intf.SysJobLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @author wande
 * @version 1.0
 * @date 2020/07/21
 */
@Service
public class SysJobLogServiceImpl implements SysJobLogService {

    @Autowired
    private SysJobLogRepository sysJobLogRepository;

    @Override
    public List<SysJobLog> selectJobLogList(SysJobLog jobLog) {
        return null;
    }

    @Override
    public SysJobLog selectJobLogById(Long jobLogId) {
        Optional<SysJobLog> sysJobLogOptional = sysJobLogRepository.findById(jobLogId);
        return sysJobLogOptional.get();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addJobLog(SysJobLog jobLog) {
        sysJobLogRepository.save(jobLog);
    }

    @Override
    public int deleteJobLogByIds(String ids) {

        return 0;
    }

    @Override
    public int deleteJobLogById(Long jobId) {
        return 0;
    }

    @Override
    public void cleanJobLog() {

    }
}

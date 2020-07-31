package com.xwder.app.sysmodules.quartz.service.impl;

import com.xwder.app.advice.TaskException;
import com.xwder.app.consts.ScheduleConstants;
import com.xwder.app.sysmodules.quartz.entity.SysJob;
import com.xwder.app.sysmodules.quartz.repository.SysJobRepository;
import com.xwder.app.sysmodules.quartz.service.intf.SysJobService;
import com.xwder.app.sysmodules.quartz.util.ScheduleUtils;
import org.quartz.JobDataMap;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author wande
 * @version 1.0
 * @date 2020/07/20
 */
@Service
@Transactional
public class SysJobServiceImpl implements SysJobService {

    @Autowired
    private Scheduler scheduler;

    @Autowired
    private SysJobRepository sysJobRepository;

    /**
     * 新增任务
     *
     * @param job 调度信息 调度信息
     */
    @Override
    @Transactional(rollbackFor = {Exception.class})
    public SysJob insertJob(SysJob job) throws SchedulerException, TaskException {
        job.setStatus(ScheduleConstants.Status.PAUSE.getValue());
        SysJob sysJob = sysJobRepository.save(job);
        ScheduleUtils.createScheduleJob(scheduler, sysJob);
        return sysJob;
    }

    /**
     * 立即运行任务
     *
     * @param job 调度信息
     */
    @Override
    public SysJob run(SysJob job) throws SchedulerException {
        Long jobId = job.getJobId();
        String jobGroup = job.getJobGroup();
        SysJob properties;
        Optional<SysJob> sysJobOptional = sysJobRepository.findById(jobId);
        properties = sysJobOptional.get();
        // 参数
        JobDataMap dataMap = new JobDataMap();
        dataMap.put(ScheduleConstants.TASK_PROPERTIES, properties);
        scheduler.triggerJob(ScheduleUtils.getJobKey(jobId, jobGroup), dataMap);
        return properties;
    }


    /**
     * 任务调度状态修改
     *
     * @param job 调度信息
     */
    @Override
    public SysJob changeStatus(SysJob job) throws SchedulerException {
        Optional<SysJob> sysJobOptional = sysJobRepository.findById(job.getJobId());
        SysJob sysJob = sysJobOptional.get();
        sysJob.setStatus(job.getStatus());
        String status = job.getStatus();
        SysJob result = null;
        if (ScheduleConstants.Status.NORMAL.getValue().equals(status)) {
            result = resumeJob(sysJob);
        } else if (ScheduleConstants.Status.PAUSE.getValue().equals(status)) {
            result = pauseJob(sysJob);
        }
        return result;
    }

    /**
     * 恢复任务
     *
     * @param job 调度信息
     */
    @Override
    @Transactional(rollbackFor = {Exception.class})
    public SysJob resumeJob(SysJob job) throws SchedulerException {
        Long jobId = job.getJobId();
        String jobGroup = job.getJobGroup();
        job.setStatus(ScheduleConstants.Status.NORMAL.getValue());
        SysJob result = sysJobRepository.save(job);
        scheduler.resumeJob(ScheduleUtils.getJobKey(jobId, jobGroup));
        return result;
    }


    /**
     * 暂停任务
     *
     * @param job 调度信息
     */
    @Override
    @Transactional(rollbackFor = {Exception.class})
    public SysJob pauseJob(SysJob job) throws SchedulerException {
        Long jobId = job.getJobId();
        String jobGroup = job.getJobGroup();
        job.setStatus(ScheduleConstants.Status.PAUSE.getValue());
        SysJob result = sysJobRepository.save(job);
        scheduler.pauseJob(ScheduleUtils.getJobKey(jobId, jobGroup));
        return result;
    }
}

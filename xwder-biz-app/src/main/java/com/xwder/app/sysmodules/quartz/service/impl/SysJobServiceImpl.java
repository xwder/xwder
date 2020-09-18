package com.xwder.app.sysmodules.quartz.service.impl;

import cn.hutool.core.util.StrUtil;
import com.xwder.app.advice.TaskException;
import com.xwder.app.consts.ScheduleConstants;
import com.xwder.app.sysmodules.quartz.dto.SysJobDto;
import com.xwder.app.sysmodules.quartz.entity.SysJob;
import com.xwder.app.sysmodules.quartz.repository.SysJobRepository;
import com.xwder.app.sysmodules.quartz.service.intf.SysJobService;
import com.xwder.app.sysmodules.quartz.util.CronUtils;
import com.xwder.app.sysmodules.quartz.util.ScheduleUtils;
import org.quartz.JobDataMap;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
     * job List
     *
     * @param sysJobDto
     * @return
     */
    @Override
    public Page<SysJob> listJob(SysJobDto sysJobDto) {
        Page<SysJob> sysJobPage = sysJobRepository.findAll(sysJobDto);
        return sysJobPage;
    }

    /**
     * 新增任务
     *
     * @param job 调度信息 调度信息
     */
    @Override
    @Transactional(rollbackFor = {Exception.class})
    public SysJob insertJob(SysJob job) throws SchedulerException, TaskException {
        if (job.getStatus() == null) {
            job.setStatus(ScheduleConstants.Status.PAUSE.getValue());
        }
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

    /**
     * 删除任务后，所对应的trigger也将被删除
     *
     * @param job 调度信息
     */
    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void deleteJob(SysJob job) throws SchedulerException {
        Long jobId = job.getJobId();
        String jobGroup = job.getJobGroup();
        sysJobRepository.deleteById(jobId);
        scheduler.deleteJob(ScheduleUtils.getJobKey(jobId, jobGroup));
    }


    /**
     * 批量删除调度信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void deleteJobByIds(String ids) throws SchedulerException {
        if (StrUtil.isNotEmpty(ids)) {
            String[] arr = ids.split(",");
            if (arr == null || arr.length < 1) {
                return;
            }
            for (int i = 0; i < arr.length; i++) {
                Long id = Long.parseLong(arr[i]);
                Optional<SysJob> optionalSysJob = sysJobRepository.findById(id);
                optionalSysJob.ifPresent((job) -> {
                    try {
                        deleteJob(job);
                    } catch (SchedulerException e) {
                        e.printStackTrace();
                    }
                });
            }
        }
    }

    /**
     * 根据id查询sysjob
     *
     * @param id
     * @return
     */
    @Override
    public SysJob selectJobById(Long id) {
        SysJob sysJob = sysJobRepository.findById(id).get();
        return sysJob;
    }

    /**
     * 更新任务的时间表达式
     *
     * @param job 调度信息
     */
    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void updateJob(SysJob job) throws SchedulerException, TaskException {
        SysJob properties = selectJobById(job.getJobId());
        sysJobRepository.save(job);
        updateSchedulerJob(job, properties.getJobGroup());
    }

    /**
     * 更新任务
     *
     * @param job      任务对象
     * @param jobGroup 任务组名
     */
    public void updateSchedulerJob(SysJob job, String jobGroup) throws SchedulerException, TaskException {
        Long jobId = job.getJobId();
        // 判断是否存在
        JobKey jobKey = ScheduleUtils.getJobKey(jobId, jobGroup);
        if (scheduler.checkExists(jobKey)) {
            // 防止创建时存在数据问题 先移除，然后在执行创建操作
            scheduler.deleteJob(jobKey);
        }
        ScheduleUtils.createScheduleJob(scheduler, job);
    }


    /**
     * 校验cron表达式是否有效
     *
     * @param cronExpression 表达式
     * @return 结果
     */
    @Override
    public boolean checkCronExpressionIsValid(String cronExpression) {
        return CronUtils.isValid(cronExpression);
    }
}

package com.xwder.app.sysmodules.quartz.service.intf;

import com.xwder.app.advice.TaskException;
import com.xwder.app.sysmodules.quartz.entity.SysJob;
import org.quartz.SchedulerException;

/**
 * 定时任务调度信息信息 服务层
 *
 * @author ruoyi
 */
public interface SysJobService {

    /**
     * 新增任务
     *
     * @param job 调度信息
     * @return 结果
     */
    SysJob insertJob(SysJob job) throws SchedulerException, TaskException;

    /**
     * 立即运行任务
     *
     * @param job 调度信息
     * @return 结果
     */
    SysJob run(SysJob job) throws SchedulerException;


    /**
     * 任务调度状态修改
     *
     * @param job 调度信息
     * @return 结果
     */
    SysJob changeStatus(SysJob job) throws SchedulerException;

    /**
     * 恢复任务
     *
     * @param job 调度信息
     * @return 结果
     */
    SysJob resumeJob(SysJob job) throws SchedulerException;

    /**
     * 暂停任务
     *
     * @param job 调度信息
     * @return 结果
     */
    SysJob pauseJob(SysJob job) throws SchedulerException;

}

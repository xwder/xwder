package com.xwder.app.sysmodules.quartz.service.intf;

import com.xwder.app.advice.TaskException;
import com.xwder.app.sysmodules.quartz.dto.SysJobDto;
import com.xwder.app.sysmodules.quartz.entity.SysJob;
import org.quartz.SchedulerException;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * 定时任务调度信息信息 服务层
 *
 * @author xwder
 */
public interface SysJobService {

    /**
     * job List
     *
     * @param sysJobDto
     * @return
     */
    Page<SysJob> listJob(SysJobDto sysJobDto);

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

    /**
     * 删除任务后，所对应的trigger也将被删除
     *
     * @param job 调度信息
     * @return 结果
     */
    void deleteJob(SysJob job) throws SchedulerException;

    /**
     * 批量删除调度信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    void deleteJobByIds(String ids) throws SchedulerException;


    /**
     * 根据id查询sysjob
     *
     * @param id
     * @return
     */
    SysJob selectJobById(Long id);

    /**
     * 更新任务
     *
     * @param job 调度信息
     * @return 结果
     */
    void updateJob(SysJob job) throws SchedulerException, TaskException;

    /**
     * 校验cron表达式是否有效
     *
     * @param cronExpression 表达式
     * @return 结果
     */
    boolean checkCronExpressionIsValid(String cronExpression);
}

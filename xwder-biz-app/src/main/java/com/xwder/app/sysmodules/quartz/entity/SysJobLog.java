package com.xwder.app.sysmodules.quartz.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author xwder
 * @Date 2020-07-20 10:15:26
 */
@Entity
@Table(name = "sys_job_log")
public class SysJobLog implements Serializable {

    private static final long serialVersionUID = 6047630553399437309L;

    /**
     * 任务日志ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "job_log_id")
    private Long jobLogId;

    /**
     * 任务名称
     */
    @Column(name = "job_name")
    private String jobName;

    /**
     * 任务组名
     */
    @Column(name = "job_group")
    private String jobGroup;

    /**
     * 调用目标字符串
     */
    @Column(name = "invoke_target")
    private String invokeTarget;

    /**
     * 日志信息
     */
    @Column(name = "job_message")
    private String jobMessage;

    /**
     * 执行状态（0正常 1失败）
     */
    @Column(name = "status")
    private String status;

    /**
     * 异常信息
     */
    @Column(name = "exception_info")
    private String exceptionInfo;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /** 开始时间 */
    @Transient
    private Date startTime;

    /** 结束时间 */
    @Transient
    private Date endTime;

    public Long getJobLogId() {
        return jobLogId;
    }

    public void setJobLogId(Long jobLogId) {
        this.jobLogId = jobLogId;
    }


    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }


    public String getJobGroup() {
        return jobGroup;
    }

    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }


    public String getInvokeTarget() {
        return invokeTarget;
    }

    public void setInvokeTarget(String invokeTarget) {
        this.invokeTarget = invokeTarget;
    }


    public String getJobMessage() {
        return jobMessage;
    }

    public void setJobMessage(String jobMessage) {
        this.jobMessage = jobMessage;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public String getExceptionInfo() {
        return exceptionInfo;
    }

    public void setExceptionInfo(String exceptionInfo) {
        this.exceptionInfo = exceptionInfo;
    }


    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }
}

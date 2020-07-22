package com.xwder.app.sysmodules.quartz.controller;

import com.xwder.app.advice.TaskException;
import com.xwder.app.sysmodules.quartz.entity.SysJob;
import com.xwder.app.sysmodules.quartz.service.intf.SysJobService;
import com.xwder.cloud.commmon.api.CommonResult;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author wande
 * @version 1.0
 * @date 2020/07/21
 */
@Controller
@RequestMapping("/monitor/job")
public class SysJobController {

    @Autowired
    private SysJobService jobService;

    /**
     * 新增保存调度
     */
    @PostMapping("/add")
    @ResponseBody
    public CommonResult addSave(@Validated SysJob job) throws SchedulerException, TaskException {
        SysJob sysJob = jobService.insertJob(job);
        return CommonResult.success(sysJob);
    }

    /**
     * 任务调度立即执行一次
     */
    @PostMapping("/run")
    @ResponseBody
    public CommonResult run(SysJob job) throws SchedulerException {
        SysJob sysJob = jobService.run(job);
        return CommonResult.success(sysJob);
    }

    /**
     * 任务调度状态修改
     */
    @PostMapping("/changeStatus")
    @ResponseBody
    public CommonResult changeStatus(SysJob job) throws SchedulerException {
        SysJob sysJob = jobService.changeStatus(job);
        return CommonResult.success(sysJob);
    }

}

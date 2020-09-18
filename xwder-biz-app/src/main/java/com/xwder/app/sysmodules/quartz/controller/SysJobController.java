package com.xwder.app.sysmodules.quartz.controller;

import com.xwder.app.advice.TaskException;
import com.xwder.app.common.result.CommonResult;
import com.xwder.app.sysmodules.quartz.dto.SysJobDto;
import com.xwder.app.sysmodules.quartz.entity.SysJob;
import com.xwder.app.sysmodules.quartz.service.intf.SysJobService;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author wande
 * @version 1.0
 * @date 2020/07/21
 */
@Controller
@RequestMapping("/sys/job")
public class SysJobController {

    @Autowired
    private SysJobService jobService;

    /**
     * 定时任务列表页面
     *
     * @return
     */
    @RequestMapping(value = "/jobListPage")
    public String listSysJobPage() {
        return "sys/job/jobList";
    }

    /**
     * 编辑定时任务页面
     *
     * @param jobId
     * @param model
     * @return
     */
    @RequestMapping(value = "/editPage")
    public String editSysJobPage(@RequestParam(required = false) Long jobId, Model model) {
        if (jobId != null) {
            SysJob sysJob = jobService.selectJobById(jobId);
            model.addAttribute("sysJob", sysJob);
        }
        return "sys/job/jobEdit";
    }

    /**
     * 分页查询job list
     *
     * @param sysJobDto
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public CommonResult list(SysJobDto sysJobDto) {
        Page<SysJob> sysJobPage = jobService.listJob(sysJobDto);
        return CommonResult.success(sysJobPage);
    }


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
     * 根据id查询sysjob
     */
    @ResponseBody
    @GetMapping("/edit/{jobId}")
    public CommonResult edit(@PathVariable("jobId") Long jobId) {
        SysJob sysJob = jobService.selectJobById(jobId);
        return CommonResult.success(sysJob);
    }

    /**
     * 修改保存调度
     */
    @PostMapping("/edit")
    @ResponseBody
    public CommonResult editSave(@Validated SysJob job) throws SchedulerException, TaskException {
        jobService.updateJob(job);
        return CommonResult.success();
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

    /**
     * 删除任务
     */
    @PostMapping("/remove")
    @ResponseBody
    public CommonResult remove(String ids) throws SchedulerException {
        jobService.deleteJobByIds(ids);
        return CommonResult.success();
    }

    /**
     * 校验cron表达式是否有效
     */
    @PostMapping("/checkCronExpressionIsValid")
    @ResponseBody
    public CommonResult checkCronExpressionIsValid(SysJob job) {
        boolean checkResut = jobService.checkCronExpressionIsValid(job.getCronExpression());
        if (checkResut) {
            return CommonResult.success();
        }
        return CommonResult.failed();
    }
}

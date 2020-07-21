package com.xwder.app.sysmodules.quartz;

import com.xwder.app.XwderApplication;
import com.xwder.app.advice.TaskException;
import com.xwder.app.sysmodules.quartz.entity.SysJob;
import com.xwder.app.sysmodules.quartz.service.intf.SysJobService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

/**
 * @author wande
 * @version 1.0
 * @date 2020/07/20
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = XwderApplication.class)
public class XwderQuartzTest {

    @Autowired
    private SysJobService sysJobService;

    @Test
    public void insertJobTest() throws TaskException, SchedulerException {
        SysJob sysJob = new SysJob();
        sysJob.setJobName("系统默认（无参");
        sysJob.setJobGroup("DEFAULT");
        sysJob.setInvokeTarget("ryTask.ryNoParams");
        sysJob.setCronExpression("0/10 * * * * ?");
        sysJob.setMisfirePolicy("3");
        sysJob.setConcurrent("1");
        sysJob.setStatus("1");
        sysJob.setCreateBy("admin");
        sysJob.setCreateTime(new Date());
        sysJob.setUpdateBy("admin");
        sysJob.setUpdateTime(new Date());
        sysJob.setRemark("remark");
        sysJobService.insertJob(sysJob);
    }

}

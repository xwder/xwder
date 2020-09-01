package com.xwder.app.sysmodules.quartz;

import com.xwder.app.XwderApplication;
import com.xwder.app.advice.TaskException;
import com.xwder.app.sysmodules.quartz.entity.SysJob;
import com.xwder.app.sysmodules.quartz.entity.SysJobLog;
import com.xwder.app.sysmodules.quartz.service.intf.SysJobLogService;
import com.xwder.app.sysmodules.quartz.service.intf.SysJobService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

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

    @Autowired
    private SysJobLogService sysJobLogService;

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

    @Test
    public void testMycat() throws InterruptedException {
        /**
         * 任务执行线程池
         */
        ExecutorService executorService =
                new ThreadPoolExecutor(8,
                        12, 20L,
                        TimeUnit.SECONDS,
                        new LinkedBlockingQueue<>(40000));
        List<SysJobLog> list = new ArrayList<>();
        for (int i = 0; i < 20000; i++) {
            SysJobLog sysJobLog = new SysJobLog();
            sysJobLog.setJobName("testMycat" + i);
            sysJobLog.setJobGroup("testMycat" + i);
            sysJobLog.setJobGroup("JobGroup" + i);
            sysJobLog.setInvokeTarget("InvokeTarget" + i);
            list.add(sysJobLog);
        }
        CompletableFuture<Void> all = null;
        for (SysJobLog sysJobLog : list) {
            // 定义任务
            CompletableFuture<SysJobLog> cf = CompletableFuture.supplyAsync(() -> {
                try {
                    sysJobLogService.addJobLog(sysJobLog);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return sysJobLog;
            }, executorService);

            all = CompletableFuture.allOf(cf);
        }
        // 开始等待所有任务执行完成
        all.join();
    }
}

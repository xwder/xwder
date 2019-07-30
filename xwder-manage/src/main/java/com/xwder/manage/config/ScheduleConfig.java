package com.xwder.manage.config;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * 多线程执行定时任务
 * 所有的定时任务都放在一个线程池中，定时任务启动时使用不同都线程。
 * 
 * @author xwder
 */
@Configuration
public class ScheduleConfig implements SchedulingConfigurer {
	@Override
	public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
		// 设定一个长度10的定时任务线程池
		taskRegistrar.setScheduler(new ScheduledThreadPoolExecutor(1,
				new BasicThreadFactory.Builder().namingPattern("task-schedule-pool-%d").daemon(true).build()));
	}
}

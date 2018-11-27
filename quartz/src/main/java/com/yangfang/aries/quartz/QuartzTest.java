package com.yangfang.aries.quartz;

import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

/**
 * 定时任务调度工具quartz
 *
 * @author 幽明
 * @serial 2018/11/16
 */
public class QuartzTest {

    public static void main(String[] args) {
        JobDetail jobDetail = JobBuilder.newJob().setJobData(new JobDataMap()).build();

        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
    }
}

package com.hisun.kugga.framework.quartz.config;

import com.hisun.kugga.framework.quartz.core.scheduler.SchedulerManager;
import org.quartz.Scheduler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 定时任务 Configuration
 */
@Configuration
@EnableScheduling // 开启 Spring 自带的定时任务
public class KuggaQuartzAutoConfiguration {

    @Bean
    public SchedulerManager schedulerManager(Scheduler scheduler) {
        return new SchedulerManager(scheduler);
    }

}

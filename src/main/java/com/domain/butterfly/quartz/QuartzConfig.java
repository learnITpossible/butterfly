package com.domain.butterfly.quartz;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * com.domain.butterfly.quartz
 * @author Mark Li
 * @version 1.0.0
 * @since 2017/12/13
 */
@Configuration
public class QuartzConfig {

    public static final String DEFAULT_GROUP = "group.report";

    public static final String JOB_PREFIX = "job.";

    public static final String TRIGGER_PREFIX = "trigger.";

    @Bean
    public Scheduler schedulerFactoryBean() throws SchedulerException {

        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();
        scheduler.start();
        return scheduler;
    }
}

package com.domain.butterfly.quartz;

import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.PreDestroy;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * com.domain.butterfly.quartz
 * @author Mark Li
 * @version 1.0.0
 * @since 2017/12/13
 */
@Component
@EnableScheduling
public class ReportSchedule {

    private static final Logger log = LoggerFactory.getLogger(ReportSchedule.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Autowired
    private Scheduler scheduler;

    @Autowired
    ReportConfigRepository reportConfigRepository;

    @Scheduled(fixedRate = 10000)
    public void report() {

        log.info("The time is now {}", dateFormat.format(new Date()));
        // 从数据库查询"可执行"配置
        List<ReportConfig> configList = reportConfigRepository.listReportConfig();
        if (CollectionUtils.isEmpty(configList)) return;
        configList.stream().filter(config -> (config.getSelectSql() != null || config.getStatisticSql() != null) && config.getCronScript() != null)
                .forEach(config -> {
                    System.out.println("schedule job...");
                    JobKey jobKey = new JobKey("job." + config.getName(), QuartzConfig.DEFAULT_GROUP);
                    try {
                        // 如果已设置定时任务，删除再新增；如果未设置定时任务，新增
                        if (scheduler.checkExists(jobKey)) {

                        }
                        JobDetail job = newJob(ReportJob.class)
                                .withIdentity(jobKey)
                                .build();
                        CronTrigger trigger = newTrigger()
                                .withIdentity("trigger." + config.getName(), QuartzConfig.DEFAULT_GROUP)
                                //                    .startNow()
                                .withSchedule(cronSchedule("0 30 14 * * ?"))
                                .build();
                        scheduler.scheduleJob(job, trigger);
                    } catch (SchedulerException e) {
                        log.error(e.getMessage(), e);
                    }
                });
    }

    @PreDestroy
    public void destroy() {

        try {
            scheduler.shutdown(true);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}

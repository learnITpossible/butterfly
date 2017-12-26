package com.domain.butterfly.quartz;

import com.domain.butterfly.constant.ReportConfigConst;
import com.domain.butterfly.dao.ReportRepository;
import com.domain.butterfly.entity.ReportConfig;
import com.domain.butterfly.mail.MailManager;
import org.apache.commons.lang.StringUtils;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    ReportRepository reportRepository;

    @Autowired
    MailManager mailManager;

    @Value("${spring.mail.admin}")
    String mailAdmin;

    @Scheduled(cron = "0/30 * 9-20 ? * MON-FRI")
    public void report() {

        log.info("The time is now {}", dateFormat.format(new Date()));
        log.info("mail admin = {}", mailAdmin);
        // 从数据库查询"待计划"配置信息
        List<ReportConfig> configList = reportRepository.listReportConfig();
        if (CollectionUtils.isEmpty(configList)) return;
        configList.stream().filter(config -> (StringUtils.isNotEmpty(config.getSelectSql()) || StringUtils.isNotEmpty(config.getStatisticSql()))
                && StringUtils.isNotEmpty(config.getCronScript()))
                .forEach(config -> {
                    log.info("start schedule job, config is {}", config);
                    JobKey jobKey = new JobKey(QuartzConfig.JOB_PREFIX + config.getName(), QuartzConfig.DEFAULT_GROUP);
                    try {
                        // 如果已设置定时任务，删除再新增；如果未设置定时任务，新增
                        if (scheduler.checkExists(jobKey)) {
                            scheduler.deleteJob(jobKey);
                        }
                        JobDetail job = newJob(ReportJob.class)
                                .withIdentity(jobKey)
                                .build();
                        job.getJobDataMap().put("reportConfig", config);
                        job.getJobDataMap().put("reportRepository", reportRepository);
                        job.getJobDataMap().put("mailManager", mailManager);
                        CronTrigger trigger = newTrigger()
                                .withIdentity(QuartzConfig.TRIGGER_PREFIX + config.getName(), QuartzConfig.DEFAULT_GROUP)
                                .withSchedule(cronSchedule(config.getCronScript()))
                                .build();
                        scheduler.scheduleJob(job, trigger);
                        reportRepository.updateConfigStatus(config.getId(), ReportConfigConst.Status.SCHEDULED.value);
                        // 是否需要立即执行
                        if (config.getRunImmediately() == ReportConfigConst.RunImmediately.YES.value) {
                            scheduler.triggerJob(jobKey);
                            reportRepository.stopRunImmediately(config.getId());
                        }
                    } catch (SchedulerException e) {
                        log.error(e.getMessage(), e);
                    }
                    log.info("end schedule...");
                });
    }

    @PreDestroy
    public void destroy() {

        log.info("destroy reportSchedule...");
        try {
            scheduler.shutdown(true);
        } catch (SchedulerException e) {
            log.error(e.getMessage(), e);
        }
        // TODO 存储已计划的任务；或者启动时自动加载
        reportRepository.recoverConfigStatus();
    }
}

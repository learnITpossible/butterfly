package com.domain.butterfly.quartz;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * com.domain.butterfly.quartz
 * @author Mark Li
 * @version 1.0.0
 * @since 2017/12/13
 */
public class ReportJob implements Job {

    private static final Logger log = LoggerFactory.getLogger(ReportJob.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        log.info("start job...");
        JobDataMap map = context.getJobDetail().getJobDataMap();

        ReportConfig config = (ReportConfig) map.get("reportConfig");
        ReportRepository reportRepository = (ReportRepository) map.get("reportRepository");
        MailManager mailManager = (MailManager) map.get("mailManager");

        log.info("config = " + config);

        reportRepository.updateConfigStatus(config.getId(), ReportConfigConst.Status.RUNNING.value);

        if (!StringUtils.isEmpty(config.getStatisticSql())) {
            log.info("start statistic...");
            try {
                reportRepository.statistic(config.getStatisticSql());
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                reportRepository.updateConfigStatus(config.getId(), ReportConfigConst.Status.EXCEPTION.value);
                return;
            }
        }

        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        }

        if (!StringUtils.isEmpty(config.getSelectSql())) {
            log.info("start select...");
            try {
                // TODO 如何防止同一时间多个job同时运行可能导致的OOM 1-通过分页拿数据 2-合理规划任务的运行时间
                List<Map<String, Object>> list = reportRepository.select(config.getSelectSql());
                // list.forEach(m -> m.forEach((k, v) -> System.out.println(k + "[" + v.getClass() + "]=" + v)));
                // export excel
                File file = ExcelUtil.writeXls(config.getReportName(), list);
                // send mail
                mailManager.sendMail(config.getReceiverMailAddress(), config.getReportName(), file);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                reportRepository.updateConfigStatus(config.getId(), ReportConfigConst.Status.EXCEPTION.value);
                return;
            }
        }

        reportRepository.updateConfigStatus(config.getId(), ReportConfigConst.Status.COMPLETE.value);

        log.info("end job...");
    }
}

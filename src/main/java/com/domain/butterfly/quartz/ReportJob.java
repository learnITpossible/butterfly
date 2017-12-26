package com.domain.butterfly.quartz;

import com.domain.butterfly.constant.ReportConfigConst;
import com.domain.butterfly.dao.ReportRepository;
import com.domain.butterfly.entity.ReportConfig;
import com.domain.butterfly.mail.MailManager;
import com.domain.butterfly.util.ExportUtil;
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

        log.info("config = {}", config);
        try {
            reportRepository.updateConfigStatus(config.getId(), ReportConfigConst.Status.RUNNING.value);

            if (!StringUtils.isEmpty(config.getStatisticSql())) {
                log.info("start statistic...");
                reportRepository.statistic(config.getStatisticSql());
            }

            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                log.error(e.getMessage(), e);
            }

            if (!StringUtils.isEmpty(config.getSelectSql())) {
                log.info("start select...");
                // TODO 如何防止同一时间多个job同时运行可能导致的OOM 1-通过分页拿数据 2-合理规划任务的运行时间
                List<Map<String, Object>> list = reportRepository.select(config.getSelectSql());
                // list.forEach(m -> m.forEach((k, v) -> System.out.println(k + "[" + v.getClass() + "]=" + v)));
                // export
                File file = ExportUtil.export(config.getExportFileType(), config.getReportName(), list);
                // send mail
                mailManager.sendMail(config.getReceiverMailAddress(), config.getCopyMailAddress(), config.getBlindCopyMailAddress(), config.getReportName(), file);
            }

            reportRepository.updateConfigStatus(config.getId(), ReportConfigConst.Status.COMPLETE.value);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            try {
                reportRepository.updateConfigStatus(config.getId(), ReportConfigConst.Status.EXCEPTION.value);
                mailManager.sendExceptionMail(config.getExceptionMailAddress(), e);
            } catch (Exception e1) {
                log.error(e.getMessage(), e1);
            }
        }

        log.info("end job...");
    }
}

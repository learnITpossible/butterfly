package com.domain.butterfly.dao;

import com.domain.butterfly.entity.ReportConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * com.domain.butterfly.quartz
 *
 * @author Mark Li
 * @version 1.0.0
 * @since 2017/12/13
 */
@Repository
public class ReportRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<ReportConfig> listReportConfig() {

        return jdbcTemplate.query("SELECT * FROM report_config WHERE status = 1", new BeanPropertyRowMapper<>(ReportConfig.class));
    }

    public void updateConfigStatus(int id, int status) {

        jdbcTemplate.update("update report_config set status = ? where id = ?", preparedStatement -> {
            preparedStatement.setInt(1, status);
            preparedStatement.setInt(2, id);
        });
    }

    public void statistic(String sql) {

        jdbcTemplate.execute(sql);
    }

    public List<Map<String, Object>> select(String sql) {

        return jdbcTemplate.queryForList(sql);
    }

}

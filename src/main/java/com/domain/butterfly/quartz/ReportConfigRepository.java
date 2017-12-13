package com.domain.butterfly.quartz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * com.domain.butterfly.quartz
 * @author Mark Li
 * @version 1.0.0
 * @since 2017/12/13
 */
@Repository
public class ReportConfigRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<ReportConfig> listReportConfig() {

        List<ReportConfig> configList = jdbcTemplate.query(
                "SELECT * FROM report_config WHERE status = 2",
                new BeanPropertyRowMapper<>(ReportConfig.class)
        );
        /*(RowMapper<ReportConfig>) (resultSet, i) -> {
            ReportConfig config = new ReportConfig();
            config.setId(resultSet.getInt("id"));
            config.setName(resultSet.getString("name"));
            config.setStatisticSql(resultSet.getString("statistic_sql"));
            config.setStatisticSqlType(resultSet.getInt("statistic_sql_type"));
            config.setSelectSql(resultSet.getString("select_sql"));
            return config;
        }*/
        return configList;
    }

    public void updateStatus(int status) {

        jdbcTemplate.update("update report_config set status = ?",
                preparedStatement -> preparedStatement.setInt(1, status));
    }
}

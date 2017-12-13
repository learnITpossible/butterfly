CREATE TABLE `report_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL DEFAULT '',
  `statistic_sql` text COMMENT '统计sql',
  `statistic_sql_type` int(4) DEFAULT '0' COMMENT '0-sql；1-存储过程；',
  `select_sql` text,
  `cron_script` varchar(255) DEFAULT NULL COMMENT 'cron表达式',
  `comment` varchar(255) DEFAULT NULL COMMENT '任务备注',
  `receiver_mail_address` varchar(255) DEFAULT NULL,
  `owner` varchar(255) DEFAULT NULL COMMENT '创建人',
  `exception_mail_address` varchar(255) DEFAULT NULL,
  `status` int(4) DEFAULT '0' COMMENT '0-初始化；1-待计划；2-已计划；3-执行中；4-执行完成；5-执行异常；9-废弃；',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
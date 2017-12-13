CREATE TABLE `report_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `statistic_sql` text COMMENT '统计sql',
  `statistic_sql_type` int(4) DEFAULT '0' COMMENT '0-sql；1-存储过程；',
  `select_sql` text,
  `cron_script` varchar(255) DEFAULT NULL COMMENT 'cron表达式',
  `comment` varchar(255) DEFAULT NULL COMMENT '任务备注',
  `receiver_mail_address` varchar(255) DEFAULT NULL,
  `owner` varchar(255) DEFAULT NULL COMMENT '创建人',
  `exception_mail_address` varchar(255) DEFAULT NULL,
  `status` int(4) DEFAULT '0' COMMENT '0-初始化；1-可执行；2-取消；9-废弃；',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

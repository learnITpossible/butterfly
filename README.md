# 可配置定时报表服务

定时从report_config表拉取status=1的报表配置信息，并组装成定时任务。
任务进行时会更新status字段，修改配置时需先根据status判断任务是否正在运行。
可以做一个web服务提供增删改查功能。

## 集成

1. Spring-boot
1. Quartz
1. Spring-mail
1. JdbcTemplate

## TODO

1. 集成spring cloud config
1. logback根据spring profile更改level
1. 增加导出文件类型export_file_type：csv、xls、xlsx
1. 邮件发送的内容通过配置加载，并增加超级接收员

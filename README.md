# 可配置定时报表服务

定时从report_config表拉取status=1的报表配置信息，并组装成定时任务。
任务进行时会更新status字段，修改配置时需先根据status判断任务是否正在运行。
可以做一个web服务提供增删改查功能。

注：启动bus刷新配置，必须以web启动项目

## 集成

1. Spring Boot
1. Quartz
1. Spring Mail
1. JdbcTemplate
1. Spring Cloud Config

## TODO

1. logback根据spring profile更改level

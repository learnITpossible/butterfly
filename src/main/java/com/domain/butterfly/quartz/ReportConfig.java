package com.domain.butterfly.quartz;

import java.util.Date;

/**
 * com.domain.butterfly.quartz
 * @author Mark Li
 * @version 1.0.0
 * @since 2017/12/13
 */
public class ReportConfig {

    private int id;
    private String name = "xxx";
    private String statisticSql;
    private int statisticSqlType;
    private String selectSql;
    private String cronScript;
    private String comment;
    private String receiverMailAddress;
    private String owner;
    private String exceptionMailAddress;
    private int status;
    private Date createTime;

    public int getId() {

        return id;
    }

    public void setId(int id) {

        this.id = id;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public String getStatisticSql() {

        return statisticSql;
    }

    public void setStatisticSql(String statisticSql) {

        this.statisticSql = statisticSql;
    }

    public int getStatisticSqlType() {

        return statisticSqlType;
    }

    public void setStatisticSqlType(int statisticSqlType) {

        this.statisticSqlType = statisticSqlType;
    }

    public String getSelectSql() {

        return selectSql;
    }

    public void setSelectSql(String selectSql) {

        this.selectSql = selectSql;
    }

    public String getCronScript() {

        return cronScript;
    }

    public void setCronScript(String cronScript) {

        this.cronScript = cronScript;
    }

    public String getComment() {

        return comment;
    }

    public void setComment(String comment) {

        this.comment = comment;
    }

    public String getReceiverMailAddress() {

        return receiverMailAddress;
    }

    public void setReceiverMailAddress(String receiverMailAddress) {

        this.receiverMailAddress = receiverMailAddress;
    }

    public String getOwner() {

        return owner;
    }

    public void setOwner(String owner) {

        this.owner = owner;
    }

    public String getExceptionMailAddress() {

        return exceptionMailAddress;
    }

    public void setExceptionMailAddress(String exceptionMailAddress) {

        this.exceptionMailAddress = exceptionMailAddress;
    }

    public int getStatus() {

        return status;
    }

    public void setStatus(int status) {

        this.status = status;
    }

    public Date getCreateTime() {

        return createTime;
    }

    public void setCreateTime(Date createTime) {

        this.createTime = createTime;
    }

    @Override
    public String toString() {

        return "ReportConfig{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", statisticSql='" + statisticSql + '\'' +
                ", statisticSqlType=" + statisticSqlType +
                ", selectSql='" + selectSql + '\'' +
                ", cronScript='" + cronScript + '\'' +
                ", comment='" + comment + '\'' +
                ", receiverMailAddress='" + receiverMailAddress + '\'' +
                ", owner='" + owner + '\'' +
                ", exceptionMailAddress='" + exceptionMailAddress + '\'' +
                ", status=" + status +
                ", createTime=" + createTime +
                '}';
    }
}

package com.tongfu.entity;

import java.util.Date;

public class ArticleStatistics {
    private Date createDate;

    private Long companyId;

    private String tableType;

    private Long acticleId;

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getTableType() {
        return tableType;
    }

    public void setTableType(String tableType) {
        this.tableType = tableType == null ? null : tableType.trim();
    }

    public Long getActicleId() {
        return acticleId;
    }

    public void setActicleId(Long acticleId) {
        this.acticleId = acticleId;
    }
}
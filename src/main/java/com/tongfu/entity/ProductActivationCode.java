package com.tongfu.entity;

import java.util.Date;

public class ProductActivationCode {
    private Long id;

    private Date createDate;

    private Date modifyDate;

    private String code;

    private Long prouctPurchaseId;

    private Long status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public Long getProuctPurchaseId() {
        return prouctPurchaseId;
    }

    public void setProuctPurchaseId(Long prouctPurchaseId) {
        this.prouctPurchaseId = prouctPurchaseId;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }
}
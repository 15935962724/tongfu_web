package com.tongfu.entity;

import java.math.BigDecimal;
import java.util.Date;

public class KnowhowCode {
    private Long id;

    private String code;

    private BigDecimal faceValue;

    private BigDecimal price;

    private Date startDate;

    private Date endDate;

    private Integer type;

    private Long knowHowId;

    private Boolean isSell;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public BigDecimal getFaceValue() {
        return faceValue;
    }

    public void setFaceValue(BigDecimal faceValue) {
        this.faceValue = faceValue;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getKnowHowId() {
        return knowHowId;
    }

    public void setKnowHowId(Long knowHowId) {
        this.knowHowId = knowHowId;
    }

    public Boolean getIsSell() {
        return isSell;
    }

    public void setIsSell(Boolean isSell) {
        this.isSell = isSell;
    }
}
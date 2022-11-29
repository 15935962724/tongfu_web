package com.tongfu.entity;

import java.util.Date;

public class Hospital {
    private Long id;

    private Date createDate;

    private String name;

    private String address;

    private String phone;

    private Long areaId;

    private String make;

    private Long hospitalRankId;

    private Long hospitalCategoryId;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public Long getAreaId() {
        return areaId;
    }

    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make == null ? null : make.trim();
    }

    public Long getHospitalRankId() {
        return hospitalRankId;
    }

    public void setHospitalRankId(Long hospitalRankId) {
        this.hospitalRankId = hospitalRankId;
    }

    public Long getHospitalCategoryId() {
        return hospitalCategoryId;
    }

    public void setHospitalCategoryId(Long hospitalCategoryId) {
        this.hospitalCategoryId = hospitalCategoryId;
    }
}
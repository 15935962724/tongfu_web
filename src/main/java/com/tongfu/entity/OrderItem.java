package com.tongfu.entity;

import java.math.BigDecimal;
import java.util.Date;

public class OrderItem {
    private Long id;

    private Date createDate;

    private Date modifyDate;

    private String fullName;

    private Boolean isGift;

    private String name;

    private BigDecimal price;

    private Integer quantity;

    private Integer returnQuantity;

    private Integer shippedQuantity;

    private String sn;

    private String thumbnail;

    private Integer weight;

    private Long orders;

    private Long product;

    private Boolean isDeleted;

    private Long company;

    private Long companyReceiver;

    private Long adviserReceivre;

    private Long productPurchaseId;

    private String purchaseName;

    private String memberCaseReport;

    private String companyCaseReport;

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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName == null ? null : fullName.trim();
    }

    public Boolean getIsGift() {
        return isGift;
    }

    public void setIsGift(Boolean isGift) {
        this.isGift = isGift;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getReturnQuantity() {
        return returnQuantity;
    }

    public void setReturnQuantity(Integer returnQuantity) {
        this.returnQuantity = returnQuantity;
    }

    public Integer getShippedQuantity() {
        return shippedQuantity;
    }

    public void setShippedQuantity(Integer shippedQuantity) {
        this.shippedQuantity = shippedQuantity;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn == null ? null : sn.trim();
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail == null ? null : thumbnail.trim();
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Long getOrders() {
        return orders;
    }

    public void setOrders(Long orders) {
        this.orders = orders;
    }

    public Long getProduct() {
        return product;
    }

    public void setProduct(Long product) {
        this.product = product;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Long getCompany() {
        return company;
    }

    public void setCompany(Long company) {
        this.company = company;
    }

    public Long getCompanyReceiver() {
        return companyReceiver;
    }

    public void setCompanyReceiver(Long companyReceiver) {
        this.companyReceiver = companyReceiver;
    }

    public Long getAdviserReceivre() {
        return adviserReceivre;
    }

    public void setAdviserReceivre(Long adviserReceivre) {
        this.adviserReceivre = adviserReceivre;
    }

    public Long getProductPurchaseId() {
        return productPurchaseId;
    }

    public void setProductPurchaseId(Long productPurchaseId) {
        this.productPurchaseId = productPurchaseId;
    }

    public String getPurchaseName() {
        return purchaseName;
    }

    public void setPurchaseName(String purchaseName) {
        this.purchaseName = purchaseName == null ? null : purchaseName.trim();
    }

    public String getMemberCaseReport() {
        return memberCaseReport;
    }

    public void setMemberCaseReport(String memberCaseReport) {
        this.memberCaseReport = memberCaseReport == null ? null : memberCaseReport.trim();
    }

    public String getCompanyCaseReport() {
        return companyCaseReport;
    }

    public void setCompanyCaseReport(String companyCaseReport) {
        this.companyCaseReport = companyCaseReport == null ? null : companyCaseReport.trim();
    }
}
package com.tongfu.entity;

import java.math.BigDecimal;
import java.util.Date;

public class ProductMeal {
    private Long id;

    private Date createDate;

    private Date modifyDate;

    private Long companyId;

    private Long productId;

    private Long productPurchaseId;

    private Long productPackageId;

    private BigDecimal price;

    private Boolean isMarketable;

    private Long sales;

    private Boolean isDeleted;

    private Long status;

    private String message;

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

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getProductPurchaseId() {
        return productPurchaseId;
    }

    public void setProductPurchaseId(Long productPurchaseId) {
        this.productPurchaseId = productPurchaseId;
    }

    public Long getProductPackageId() {
        return productPackageId;
    }

    public void setProductPackageId(Long productPackageId) {
        this.productPackageId = productPackageId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Boolean getIsMarketable() {
        return isMarketable;
    }

    public void setIsMarketable(Boolean isMarketable) {
        this.isMarketable = isMarketable;
    }

    public Long getSales() {
        return sales;
    }

    public void setSales(Long sales) {
        this.sales = sales;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message == null ? null : message.trim();
    }
}
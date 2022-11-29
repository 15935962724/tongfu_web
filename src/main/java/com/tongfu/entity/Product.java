package com.tongfu.entity;

import java.math.BigDecimal;
import java.util.Date;

public class Product {
    private Long id;

    private Date createDate;

    private Date modifyDate;

    private Integer allocatedStock;

    private BigDecimal cost;

    private String fullName;

    private Long hits;

    private String image;

    private Boolean isGift;

    private Boolean isList;

    private Boolean isMarketable;

    private Boolean isTop;

    private String keyword;

    private BigDecimal marketPrice;

    private String memo;

    private String name;

    private Long point;

    private BigDecimal price;

    private Long sales;

    private String sn;

    private Integer stock;

    private String unit;

    private Integer weight;

    private Long brand;

    private Long productCategory;

    private Boolean isDeleted;

    private Long companyId;

    private Long status;

    private Long activationMode;

    private Boolean isHideprice;

    private String priceExplain;

    private String manufactor;

    private String url;

    private String softwareSize;

    private String softwareVersion;

    private Boolean isRelatedSoftware;

    private String relationSoftwareUrl;

    private String productPackage;

    private String productPackageUrl;

    private Boolean isShowproductPackageUrl;

    private String ontrialPackage;

    private String ontrialPackageUrl;

    private Boolean isShowOntrialPackageUrl;

    private Boolean isDemonstration;

    private Boolean isRecommend;

    private Long orders;

    private String video;

    private String promotion;

    private Long isAddService;

    private Long productId;

    private Integer reserveStock;

    private String language;

    private String apparatusCategory;

    private String authenticationMessage;

    private String system;

    private String message;

    private Boolean isScientific;

    private String introduction;

    private String recommendMemo;

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction == null ? null : introduction.trim();
    }

    public String getRecommendMemo() {
        return recommendMemo;
    }

    public void setRecommendMemo(String recommendMemo) {
        this.recommendMemo = recommendMemo == null ? null : recommendMemo.trim();
    }

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

    public Integer getAllocatedStock() {
        return allocatedStock;
    }

    public void setAllocatedStock(Integer allocatedStock) {
        this.allocatedStock = allocatedStock;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName == null ? null : fullName.trim();
    }

    public Long getHits() {
        return hits;
    }

    public void setHits(Long hits) {
        this.hits = hits;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image == null ? null : image.trim();
    }

    public Boolean getIsGift() {
        return isGift;
    }

    public void setIsGift(Boolean isGift) {
        this.isGift = isGift;
    }

    public Boolean getIsList() {
        return isList;
    }

    public void setIsList(Boolean isList) {
        this.isList = isList;
    }

    public Boolean getIsMarketable() {
        return isMarketable;
    }

    public void setIsMarketable(Boolean isMarketable) {
        this.isMarketable = isMarketable;
    }

    public Boolean getIsTop() {
        return isTop;
    }

    public void setIsTop(Boolean isTop) {
        this.isTop = isTop;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword == null ? null : keyword.trim();
    }

    public BigDecimal getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(BigDecimal marketPrice) {
        this.marketPrice = marketPrice;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo == null ? null : memo.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Long getPoint() {
        return point;
    }

    public void setPoint(Long point) {
        this.point = point;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Long getSales() {
        return sales;
    }

    public void setSales(Long sales) {
        this.sales = sales;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn == null ? null : sn.trim();
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit == null ? null : unit.trim();
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Long getBrand() {
        return brand;
    }

    public void setBrand(Long brand) {
        this.brand = brand;
    }

    public Long getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(Long productCategory) {
        this.productCategory = productCategory;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public Long getActivationMode() {
        return activationMode;
    }

    public void setActivationMode(Long activationMode) {
        this.activationMode = activationMode;
    }

    public Boolean getIsHideprice() {
        return isHideprice;
    }

    public void setIsHideprice(Boolean isHideprice) {
        this.isHideprice = isHideprice;
    }

    public String getPriceExplain() {
        return priceExplain;
    }

    public void setPriceExplain(String priceExplain) {
        this.priceExplain = priceExplain == null ? null : priceExplain.trim();
    }

    public String getManufactor() {
        return manufactor;
    }

    public void setManufactor(String manufactor) {
        this.manufactor = manufactor == null ? null : manufactor.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getSoftwareSize() {
        return softwareSize;
    }

    public void setSoftwareSize(String softwareSize) {
        this.softwareSize = softwareSize == null ? null : softwareSize.trim();
    }

    public String getSoftwareVersion() {
        return softwareVersion;
    }

    public void setSoftwareVersion(String softwareVersion) {
        this.softwareVersion = softwareVersion == null ? null : softwareVersion.trim();
    }

    public Boolean getIsRelatedSoftware() {
        return isRelatedSoftware;
    }

    public void setIsRelatedSoftware(Boolean isRelatedSoftware) {
        this.isRelatedSoftware = isRelatedSoftware;
    }

    public String getRelationSoftwareUrl() {
        return relationSoftwareUrl;
    }

    public void setRelationSoftwareUrl(String relationSoftwareUrl) {
        this.relationSoftwareUrl = relationSoftwareUrl == null ? null : relationSoftwareUrl.trim();
    }

    public String getProductPackage() {
        return productPackage;
    }

    public void setProductPackage(String productPackage) {
        this.productPackage = productPackage == null ? null : productPackage.trim();
    }

    public String getProductPackageUrl() {
        return productPackageUrl;
    }

    public void setProductPackageUrl(String productPackageUrl) {
        this.productPackageUrl = productPackageUrl == null ? null : productPackageUrl.trim();
    }

    public Boolean getIsShowproductPackageUrl() {
        return isShowproductPackageUrl;
    }

    public void setIsShowproductPackageUrl(Boolean isShowproductPackageUrl) {
        this.isShowproductPackageUrl = isShowproductPackageUrl;
    }

    public String getOntrialPackage() {
        return ontrialPackage;
    }

    public void setOntrialPackage(String ontrialPackage) {
        this.ontrialPackage = ontrialPackage == null ? null : ontrialPackage.trim();
    }

    public String getOntrialPackageUrl() {
        return ontrialPackageUrl;
    }

    public void setOntrialPackageUrl(String ontrialPackageUrl) {
        this.ontrialPackageUrl = ontrialPackageUrl == null ? null : ontrialPackageUrl.trim();
    }

    public Boolean getIsShowOntrialPackageUrl() {
        return isShowOntrialPackageUrl;
    }

    public void setIsShowOntrialPackageUrl(Boolean isShowOntrialPackageUrl) {
        this.isShowOntrialPackageUrl = isShowOntrialPackageUrl;
    }

    public Boolean getIsDemonstration() {
        return isDemonstration;
    }

    public void setIsDemonstration(Boolean isDemonstration) {
        this.isDemonstration = isDemonstration;
    }

    public Boolean getIsRecommend() {
        return isRecommend;
    }

    public void setIsRecommend(Boolean isRecommend) {
        this.isRecommend = isRecommend;
    }

    public Long getOrders() {
        return orders;
    }

    public void setOrders(Long orders) {
        this.orders = orders;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video == null ? null : video.trim();
    }

    public String getPromotion() {
        return promotion;
    }

    public void setPromotion(String promotion) {
        this.promotion = promotion == null ? null : promotion.trim();
    }

    public Long getIsAddService() {
        return isAddService;
    }

    public void setIsAddService(Long isAddService) {
        this.isAddService = isAddService;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getReserveStock() {
        return reserveStock;
    }

    public void setReserveStock(Integer reserveStock) {
        this.reserveStock = reserveStock;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language == null ? null : language.trim();
    }

    public String getApparatusCategory() {
        return apparatusCategory;
    }

    public void setApparatusCategory(String apparatusCategory) {
        this.apparatusCategory = apparatusCategory == null ? null : apparatusCategory.trim();
    }

    public String getAuthenticationMessage() {
        return authenticationMessage;
    }

    public void setAuthenticationMessage(String authenticationMessage) {
        this.authenticationMessage = authenticationMessage == null ? null : authenticationMessage.trim();
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system == null ? null : system.trim();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message == null ? null : message.trim();
    }

    public Boolean getIsScientific() {
        return isScientific;
    }

    public void setIsScientific(Boolean isScientific) {
        this.isScientific = isScientific;
    }
}
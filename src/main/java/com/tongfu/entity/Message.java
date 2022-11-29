package com.tongfu.entity;

import java.util.Date;

public class Message {
    private Long id;

    private Date createDate;

    private String ip;

    private Long forMessage;

    private Long receiver;

    private Long sender;

    private Boolean isForwardReceiver;

    private Boolean isForwardSender;

    private Long operator;

    private Long productId;

    private Boolean isHandle;

    private Long companyId;

    private String email;

    private String content;

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

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    public Long getForMessage() {
        return forMessage;
    }

    public void setForMessage(Long forMessage) {
        this.forMessage = forMessage;
    }

    public Long getReceiver() {
        return receiver;
    }

    public void setReceiver(Long receiver) {
        this.receiver = receiver;
    }

    public Long getSender() {
        return sender;
    }

    public void setSender(Long sender) {
        this.sender = sender;
    }

    public Boolean getIsForwardReceiver() {
        return isForwardReceiver;
    }

    public void setIsForwardReceiver(Boolean isForwardReceiver) {
        this.isForwardReceiver = isForwardReceiver;
    }

    public Boolean getIsForwardSender() {
        return isForwardSender;
    }

    public void setIsForwardSender(Boolean isForwardSender) {
        this.isForwardSender = isForwardSender;
    }

    public Long getOperator() {
        return operator;
    }

    public void setOperator(Long operator) {
        this.operator = operator;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Boolean getIsHandle() {
        return isHandle;
    }

    public void setIsHandle(Boolean isHandle) {
        this.isHandle = isHandle;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}
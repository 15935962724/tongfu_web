package com.tongfu.email;

/**
 * 发送邮件
 */
public class MailInfo{

    // 发件人账户
    private String sendEmailAccount;

    // 发件人密码
    private String sendEmailPassword;

    // 收件人账户
    private String receiveMailAccount;

    // 发送人姓名
    private String sendPersonName;

    // 收件人姓名
    private String receivePersonName;

    // 邮件标题
    private String mailTitle;

    // 邮件正文
    private String mailContent;

    //附件路径
    private String fileName;

    public MailInfo(String sendEmailAccount, String sendEmailPassword, String receiveMailAccount,
                    String sendPersonName, String receivePersonName,
                    String mailTitle, String mailContent,String fileName) {
        this.sendEmailAccount = sendEmailAccount;
        this.sendEmailPassword = sendEmailPassword;
        this.receiveMailAccount = receiveMailAccount;
        this.sendPersonName = sendPersonName;
        this.receivePersonName = receivePersonName;
        this.mailTitle = mailTitle;
        this.mailContent = mailContent;
        this.fileName= fileName;
    }

    public String getSendEmailAccount() {
        return sendEmailAccount;
    }

    public void setSendEmailAccount(String sendEmailAccount) {
        this.sendEmailAccount = sendEmailAccount;
    }

    public String getSendEmailPassword() {
        return sendEmailPassword;
    }

    public void setSendEmailPassword(String sendEmailPassword) {
        this.sendEmailPassword = sendEmailPassword;
    }

    public String getReceiveMailAccount() {
        return receiveMailAccount;
    }

    public void setReceiveMailAccount(String receiveMailAccount) {
        this.receiveMailAccount = receiveMailAccount;
    }

    public String getMailTitle() {
        return mailTitle;
    }

    public void setMailTitle(String mailTitle) {
        this.mailTitle = mailTitle;
    }

    public String getMailContent() {
        return mailContent;
    }

    public void setMailContent(String mailContent) {
        this.mailContent = mailContent;
    }

    public String getSendPersonName() {
        return sendPersonName;
    }

    public void setSendPersonName(String sendPersonName) {
        this.sendPersonName = sendPersonName;
    }

    public String getReceivePersonName() {
        return receivePersonName;
    }

    public void setReceivePersonName(String receivePersonName) {
        this.receivePersonName = receivePersonName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
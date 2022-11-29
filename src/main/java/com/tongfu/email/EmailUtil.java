package com.tongfu.email;

/**
 * 发送邮件工具
 */
public class EmailUtil {

    public static void main(String[] args) {

        EmailEntity email = new EmailEntity();
        email.setUserName("15935962724@163.com");
        email.setPassword("ZhuGeSiMa123");
        email.setHost("smtp.163.com");
        email.setPort(25);
        email.setFromAddress("15935962724@163.com");
        email.setToAddress("1263451851@qq.com");
        email.setSubject("这是一封测试邮件!!!!");
        email.setContext("看看这是什么");
        email.setContextType("text/html;charset=utf-8");
        boolean flag = EmailSend.EmailSendTest(email);
        System.err.println("邮件发送结果=="+flag);

    }

}
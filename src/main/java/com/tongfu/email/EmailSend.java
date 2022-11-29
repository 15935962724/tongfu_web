package com.tongfu.email;


import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.UnsupportedEncodingException;
import java.util.*;


/**
 * 发送邮件
 */
public class EmailSend {

    public static boolean EmailSendTest(EmailEntity emailEntity){
        try {
            //配置文件
            Properties properties = new Properties();
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.host", emailEntity.getHost());
            properties.put("mail.smtp.port", 25);
            properties.put("mail.smtp.starrttls.enable", "true");
            //创建会话
            VerifyEmail verifyEmail = new VerifyEmail(emailEntity.getUserName(), emailEntity.getPassword());
            Session mailSession = Session.getInstance(properties, verifyEmail);
            mailSession.setDebug(true);
            //创建信息对象
            Message message = new MimeMessage(mailSession);
            InternetAddress from = new InternetAddress(emailEntity.getFromAddress());
            InternetAddress to = new InternetAddress(emailEntity.getToAddress());
            //设置邮件信息的来源
            message.setFrom(from);
            //设置邮件的接收者
            message.setRecipient(MimeMessage.RecipientType.TO, to);
            message.setSubject(emailEntity.getSubject());
            //设置邮件发送日期
            message.setSentDate(new Date());
//            //设置邮件内容
//            message.setContent(emailEntity.getContext() , emailEntity.getContextType());

            Multipart mp = new MimeMultipart();
                         MimeBodyPart mbpContent = new MimeBodyPart();
                         mbpContent.setText(emailEntity.getContext());
                         mp.addBodyPart(mbpContent);

            /* 往邮件中添加附件 */
            String fileName = emailEntity.getFilename();
            Vector<String> file = emailEntity.getFile();
                        if (file!= null) {
                                 Enumeration<String> efile = file.elements();
                                 while (efile.hasMoreElements()) {
                                         MimeBodyPart mbpFile = new MimeBodyPart();
                                         fileName = efile.nextElement().toString();
                                         FileDataSource fds = new FileDataSource(fileName);
                                         mbpFile.setDataHandler(new DataHandler(fds));

                                         String text = MimeUtility.encodeText(new String(fds.getName().getBytes(), "GB2312"), "GB2312", "B");
                                         mbpFile.setFileName(text);
                                     mp.addBodyPart(mbpFile);
                                     }
                                 System.out.println("添加成功");
                             }
            message.setContent(mp);
            message.saveChanges();
            //发送邮件
            Transport transport = mailSession.getTransport("smtp");
            transport.connect(emailEntity.getHost(), emailEntity.getUserName(), emailEntity.getPassword());
            System.out.println("发送:" + transport);
            transport.sendMessage(message, message.getAllRecipients());
            System.out.println("success");
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("fial...");
            return false;

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return false;
        }

    }

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
        email.attachFile("E:\\upload\\avatar1.jpg"); // 往邮件中添加附件
        email.attachFile("E:\\upload\\avatar2.jpg");
//        email.attachFile("往邮件中添加附件");
        boolean flag = EmailSend.EmailSendTest(email);
        System.err.println("邮件发送结果=="+flag);

    }

}
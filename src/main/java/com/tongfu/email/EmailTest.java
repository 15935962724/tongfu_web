package com.tongfu.email;

//import org.springframework.core.io.ClassPathResource;
//import org.springframework.core.io.FileSystemResource;
//import org.springframework.mail.javamail.JavaMailSenderImpl;
//import org.springframework.mail.javamail.MimeMessageHelper;
//
//import javax.mail.MessagingException;
//import javax.mail.internet.MimeMessage;

/**
 * 发送邮件工具
 */
public class EmailTest {
//
//    // Spring的邮件工具类，实现了MailSender和JavaMailSender接口
//    private JavaMailSenderImpl mailSender;
//
//    public EmailTest() {
//        // 初始化JavaMailSenderImpl，当然推荐在spring配置文件中配置，这里是为了简单
//        mailSender = new JavaMailSenderImpl();
//        // 设置参数
//        mailSender.setHost("smtp.qq.com");
//        mailSender.setUsername("mosaic@qq.com");
//        mailSender.setPassword("asterisks");
//    }
//
//
//    public void attachedSend() throws MessagingException {
//        //使用JavaMail的MimeMessage，支付更加复杂的邮件格式和内容
//        MimeMessage msg = mailSender.createMimeMessage();
//
//        //创建MimeMessageHelper对象，处理MimeMessage的辅助类
//        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
//
//        //使用辅助类MimeMessage设定参数
//        helper.setFrom(mailSender.getUsername());
//        helper.setTo("mosaic@126.com");
//        helper.setSubject("Hello Attachment");
//        helper.setText("This is a mail with attachment");
//
//        //加载文件资源，作为附件
//        ClassPathResource file = new ClassPathResource("Chrysanthemum.jpg");
//
//        //加入附件
//        helper.addAttachment("attachment.jpg", file);
//
//        //发送邮件
//        mailSender.send(msg);
//    }
//
//
//    /**发送富文本邮件
//     * @throws MessagingException
//     */
//    public void richContentSend() throws MessagingException {
//        MimeMessage msg = mailSender.createMimeMessage();
//
//        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
//
//        helper.setFrom(mailSender.getUsername());
//        helper.setTo("mosaic@126.com");
//        helper.setSubject("Rich content mail");
//        //第二个参数true，表示text的内容为html，然后注意<img/>标签，src='cid:file'，'cid'是contentId的缩写，'file'是一个标记，需要在后面的代码中调用MimeMessageHelper的addInline方法替代成文件
//        helper.setText(
//                "<body><p>Hello Html Email</p><img src='cid:file'/></body>",
//                true);
//
//        FileSystemResource file = new FileSystemResource(
//                "C:\\Users\\Public\\Pictures\\Sample Pictures\\Chrysanthemum.jpg");
//        helper.addInline("file", file);
//
//        mailSender.send(msg);
//    }


}
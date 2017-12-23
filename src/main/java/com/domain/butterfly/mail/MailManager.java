package com.domain.butterfly.mail;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.*;
import java.io.File;
import java.util.Arrays;
import java.util.Date;
import java.util.Properties;

/**
 * com.domain.butterfly.quartz
 *
 * @author Mark Li
 * @version 1.0.0
 * @since 2017/12/14
 */
@Component
public class MailManager {

    private static final Logger log = LoggerFactory.getLogger(MailManager.class);

    @Value("${spring.mail.username}")
    private String mailFrom;

    @Autowired
    JavaMailSender mailSender;

    public void sendMail(String receiverAddress, String copyAddress, String blindCopyAddress, String title, File file) throws MessagingException {

        MimeMessage msg = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg, true, "UTF-8");
        helper.setFrom(mailFrom);
        helper.setTo(receiverAddress.split(","));
        if (StringUtils.isNotEmpty(copyAddress)) {
            helper.setCc(copyAddress.split(","));
        }
        if (StringUtils.isNotEmpty(blindCopyAddress)) {
            helper.setBcc(blindCopyAddress.split(","));
        }
        helper.setSubject(title);
        FileSystemResource attachment = new FileSystemResource(new File("/Users/lijing/财务应付明细.xlsx"));
        helper.setText("定时报表:" + attachment.getFilename());
        helper.addAttachment(attachment.getFilename(), attachment);
        mailSender.send(msg);
    }

    public void sendMail1(String receiverAddress, String copyAddress, String blindCopyAddress, String title, File file) throws Exception {

        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.smtp.host", "smtp.exmail.qq.com");
        props.setProperty("mail.smtp.auth", "true");
        /*final String smtpPort = "465";
        props.setProperty("mail.smtp.port", smtpPort);
        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.socketFactory.port", smtpPort);*/

        Session session = Session.getInstance(props);
        // session.setDebug(true);

        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress("dapan_statistics@weyao.com"));
        message.addRecipient(MimeMessage.RecipientType.TO, new InternetAddress("490960362@qq.com"));
        message.setSubject("邮件主题");

        MimeBodyPart content = new MimeBodyPart();
        content.setText("邮件内容");

        MimeBodyPart attachment = new MimeBodyPart();
        DataHandler dataHandler = new DataHandler(new FileDataSource(new File("/Users/lijing/财务应付明细2017-12-23.xlsx")));
        attachment.setDataHandler(dataHandler);
        attachment.setFileName(MimeUtility.encodeText(dataHandler.getName()));

        MimeMultipart multipart = new MimeMultipart();
        multipart.addBodyPart(content);
        multipart.addBodyPart(attachment);
        multipart.setSubType("mixed");
        message.setContent(multipart);

        message.setSentDate(new Date());
        message.saveChanges();

        Transport transport = session.getTransport();
//        transport.connect("490960362@qq.com", "wxcjnlfnkcuybgbd");
//        transport.connect("jo490960362@126.com", "1qaz2wsx");
        transport.connect("dapan_statistics@weyao.com", "IDer23$3akS");
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
    }

    public void sendExceptionMail(String exceptionAddress, Exception e) {

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(mailFrom);
        msg.setTo(exceptionAddress.split(","));
        msg.setSubject("【异常】报表任务发生异常");
        msg.setText(e.toString() + "\r\n" + Arrays.toString(e.getStackTrace()));
        mailSender.send(msg);
    }
}

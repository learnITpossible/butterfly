package com.domain.butterfly.quartz;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

/**
 * com.domain.butterfly.quartz
 * @author Mark Li
 * @version 1.0.0
 * @since 2017/12/14
 */
@Component
public class MailManager {

    private static final Logger log = LoggerFactory.getLogger(MailManager.class);

    @Value("${spring.mail.username}")
    private String mail_from;

    @Autowired
    JavaMailSender mailSender;

    public void sendMail(String receiverAddress, String title, File file) {

        MimeMessage msg = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(msg, true, "UTF-8");
            helper.setFrom(mail_from);
            helper.setTo(receiverAddress.split(","));
            helper.setSubject(title);
            helper.setText("定时报表");
            String originFileName = file.getName();
            String fileName = originFileName.substring(0, originFileName.lastIndexOf("."));
            helper.addAttachment(fileName, file);
        } catch (MessagingException e) {
            log.error(e.getMessage(), e);
        }
        mailSender.send(msg);
    }

    public void sendExceptionMail(String exceptionAddress, Exception e) {

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(mail_from);
        msg.setTo(exceptionAddress.split(","));
        msg.setSubject("【异常】报表任务发生异常");
        msg.setText(e.getMessage());
        mailSender.send(msg);
    }
}

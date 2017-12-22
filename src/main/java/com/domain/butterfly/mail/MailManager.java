package com.domain.butterfly.mail;

import org.apache.commons.lang.StringUtils;
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
import java.util.Arrays;

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

    public void sendMail(String receiverAddress, String copyAddress, String blindCopyAddress, String title, File file) throws MessagingException {

        MimeMessage msg = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg, true, "UTF-8");
        helper.setFrom(mail_from);
        helper.setTo(receiverAddress.split(","));
        if (StringUtils.isNotEmpty(copyAddress)) {
            helper.setCc(copyAddress.split(","));
        }
        if (StringUtils.isNotEmpty(blindCopyAddress)) {
            helper.setBcc(blindCopyAddress.split(","));
        }
        helper.setSubject(title);
        helper.setText("定时报表");
        helper.addAttachment(file.getName(), file);
        mailSender.send(msg);
    }

    public void sendExceptionMail(String exceptionAddress, Exception e) {

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(mail_from);
        msg.setTo(exceptionAddress.split(","));
        msg.setSubject("【异常】报表任务发生异常");
        msg.setText(e.toString() + "\r\n" + Arrays.toString(e.getStackTrace()));
        mailSender.send(msg);
    }
}

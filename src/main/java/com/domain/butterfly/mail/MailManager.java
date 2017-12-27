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
import java.util.*;

/**
 * com.domain.butterfly.quartz
 *
 * @author Mark Li
 * @version 1.0.0
 * @since 2017/12/14
 */
@Component
//@RefreshScope
public class MailManager {

    private static final Logger log = LoggerFactory.getLogger(MailManager.class);

    @Value("${spring.mail.username}")
    private String mailFrom;

    @Value("${spring.mail.admin}")
    private String mailAdmin;

    @Autowired
    JavaMailSender mailSender;

    /**
     * @see <a href="https://kb.superb.net/index.php/email-support/14-email-troubleshooting/15-why-are-my-email-attachments-renamed-to-dat-or-bin">eamil</a>
     * Why are my email attachments renamed to .DAT or .BIN?
     * Some e-mail clients (also known as e-mail MUA) adhere to the new standard RFC2231 for attachment filename handling.
     * Superb's  webmail, Thunderbird 1.5, and some newer e-mail clients adhere to these new standards.
     * The new standard requires that any spaces and special characters in the attachment filenames are converted to their MIME equivalent.
     * For example, a file name my document.doc is converted to my_document.doc.
     * The problem is that some e-mail clients, including Outlook and some older clients still use the old standard RFC2047.
     * As a result, they do not recognize the converted filename format and surprisingly renames those attachments to .BIN or .DAT
     * Solution: Use e-mail clients that support RFC2231 standard.
     * Workaround: If you cannot use newer e-mail clients or those that support RFC2231,
     * you can avoid this problem by using filenames that do not contain spaces or special characters.
     * For example, if you want to attach a file that has a space in the filename such as my document.doc,
     * just rename it as my_document.doc.
     */
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
        FileSystemResource attachment = new FileSystemResource(file);
        helper.setText("定时报表:" + attachment.getFilename());
        helper.addAttachment(attachment.getFilename(), attachment);
        mailSender.send(msg);
    }

    @Deprecated
    public void sendMailJava(String receiverAddress, String copyAddress, String blindCopyAddress, String title, File file) throws Exception {

        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.smtp.host", "smtp.exmail.qq.com");
        props.setProperty("mail.smtp.auth", "true");

        Session session = Session.getInstance(props, null);
        // session.setDebug(true);

        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(mailFrom));
        message.addRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiverAddress));
        message.setSubject("邮件主题");
        /*message.setSubject(MimeUtility.encodeText("邮件主题", "UTF-8", "B"));
        message.isMimeType("text/html");*/

        MimeBodyPart content = new MimeBodyPart();
        content.setText("邮件内容");
        /*content.setContent("邮件内容", "text/html; charset=UTF-8");*/

        MimeBodyPart attachment = new MimeBodyPart();
        DataHandler dataHandler = new DataHandler(new FileDataSource(file));
        attachment.setDataHandler(dataHandler);
        attachment.setFileName(MimeUtility.encodeText(dataHandler.getName()));

        MimeMultipart multipart = new MimeMultipart();
        multipart.addBodyPart(content);
        multipart.addBodyPart(attachment);
        multipart.setSubType("mixed");
        message.setContent(multipart);

        message.setSentDate(new Date());
        message.saveChanges();

        Transport transport = session.getTransport("smtp");
        transport.connect(mailFrom, "IDer23$3akS");
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
    }

    public void sendExceptionMail(String exceptionAddress, Exception e) {

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(mailFrom);
        List<String> addressList = new ArrayList<>(Arrays.asList(exceptionAddress.split(",")));
        addressList.add(mailAdmin);
        msg.setTo(addressList.toArray(new String[addressList.size()]));
        msg.setSubject("【异常】报表任务发生异常");
        msg.setText(e.toString() + "\r\n" + Arrays.toString(e.getStackTrace()));
        mailSender.send(msg);
    }
}

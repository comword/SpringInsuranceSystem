package org.gtdev.webapps.iaatraesamhsaat.mail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;

@Service
public class EmailService {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    JavaMailSender javaMailSender;

    public static class EmailDetails {
        private short type; // 0 simpleEmail, 1 MimeEmail
        private String emailTo, emailSubject, emailCC, emailBCC, emailBody;
        private Map<String, String> attachmentPaths;
    }

    public void sendEmail(EmailDetails emailDetails) throws MessagingException, FileNotFoundException {
        String CC = emailDetails.emailCC;
        String BCC = emailDetails.emailBCC;
        if(emailDetails.type == 0) {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper mailHelper = new MimeMessageHelper(message);
            mailHelper.setTo(emailDetails.emailTo);
            mailHelper.setText(emailDetails.emailBody);
            mailHelper.setSubject(emailDetails.emailSubject);
            if(CC != null)
                mailHelper.setCc(CC);
            if(BCC != null)
                mailHelper.setBcc(BCC);
            if (emailDetails.attachmentPaths != null) //load attachments
                for (String fname:emailDetails.attachmentPaths.keySet()) {
                    File file = ResourceUtils.getFile(emailDetails.attachmentPaths.get(fname)); // file:/.../.../...
                    mailHelper.addAttachment(fname, file);
                }
            javaMailSender.send(message);
        } else if (emailDetails.type == 1) {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(emailDetails.emailTo);
            message.setSubject(emailDetails.emailSubject);
            message.setText(emailDetails.emailBody);
            if(CC != null)
                message.setCc(CC);
            if(BCC != null)
                message.setBcc(BCC);
            javaMailSender.send(message);
        }
    }
}

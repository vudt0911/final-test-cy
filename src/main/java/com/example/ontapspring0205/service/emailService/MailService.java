package com.example.ontapspring0205.service.emailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.util.Map;


@Service
public class MailService {
    private static final String CONTENT_TYPE_TEXT_HTML = "text/html;charset=\"utf-8\"";
    public static final String MAIL_FROM = "dinhvu091193@gmail.com";
    private final String MAIL_TEMPLATE_PATH = "user/";
    @Autowired
    JavaMailSender javaMailSender;
    @Autowired
    TemplateEngine templateEngine;

    public void sendTextMail(String from, String toAddress, String subject, Map<String, Object> content, String template) {
        try {
            //prepare message using a Spring helpers
            final MimeMessage message = this.javaMailSender.createMimeMessage();
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(toAddress));
            message.setFrom(from);
            message.setSubject(subject);

            Context context = new Context();
            if (content != null)
                context.setVariables(content);
            message.setContent(this.templateEngine.process(MAIL_TEMPLATE_PATH + template, context), CONTENT_TYPE_TEXT_HTML);
            //send email
            this.javaMailSender.send(message);
        } catch (MessagingException e) {
            System.out.println("error send email" + e.getMessage());
        }
    }

    public void sendMailWithFile(String from, String toAddress, String subject, Map<String, Object> content, String template, String... files) {
        try {
            //prepare message using a Spring helpers
            final MimeMessage message = this.javaMailSender.createMimeMessage();
            final MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, true);
            mimeMessageHelper.setTo(toAddress);
            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setSubject(subject);

            Context context = new Context();
            if (content != null)
                context.setVariables(content);
            mimeMessageHelper.setText(this.templateEngine.process(MAIL_TEMPLATE_PATH + template, context), true);

            if (files != null) {
                for (String file : files) {
                    File f = new File(file);
                    mimeMessageHelper.addAttachment(f.getName(), () -> new FileInputStream(file));
                }
            }
            //send email
            this.javaMailSender.send(message);
        } catch (MessagingException e) {
            System.out.println("error send email" + e.getMessage());
        }
    }

}

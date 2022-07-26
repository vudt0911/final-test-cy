package com.example.ontapspring0205.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig {
    @Value("${mail.smtp.host}")
    private String host;
    @Value("${mail.smtp.port}")
    private int port;
    @Value("${mail.smtp.username}")
    private String username;
    @Value("${mail.smtp.password}")
    private String password;
    @Value("${mail.smtp.auth}")
    private String auth;
    @Bean
    public JavaMailSender getMailSender() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(host);
        javaMailSender.setPort(port);
        javaMailSender.setUsername(username);
        javaMailSender.setPassword(password);

        Properties javaMailProperties = new Properties();
        javaMailProperties.put("mail.transport.protocol", "smtp");
        javaMailProperties.put("mail.smtp.auth", auth);
        javaMailProperties.put("mail.smtp.starttls.enable", "true");
        javaMailProperties.put("mail.debug", "false");
        javaMailProperties.put("mail.smtp.ssl.trust", "*");

        javaMailSender.setJavaMailProperties(javaMailProperties);
        return javaMailSender;
    }
}

package com.example.ontapspring0205.service.emailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ResetPasswordService implements AuthService{
    @Autowired MailService mailService;
    @Override
    public void sendMail(String email) {
        Map<String, Object> content = new HashMap<>();
        content.put("email", email);
        content.put("token", "12345");
//        content.put("resetPass", resetPass);
        this.mailService.sendTextMail(MailService.MAIL_FROM, email, "Thư xác nhận đổi Password", content, "formConfirmResetPassword.html");
    }

    public boolean checkToken(String token){
        return true;
    }
}

package com.example.ontapspring0205.controller;

import com.example.ontapspring0205.dto.OrderLine;
import com.example.ontapspring0205.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class MailController {
    @Autowired
    JavaMailSender javaMailSender;
    @Autowired
    CartService cartService;

    @RequestMapping(value = "/send-email-thymeleaf", method = RequestMethod.POST)
    public String sendMail(@SessionAttribute("CART") Map<Long, OrderLine> cart, @ModelAttribute("xacnhan") String emailUser, HttpSession session) {
        String email = emailUser;
        cartService.checkout(cart, email, cartService.getCart(session));
        return "user/formCheckMail";
    }
}
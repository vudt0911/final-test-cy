package com.example.ontapspring0205.security.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthenSuccessHandlerLogin implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        UserDetailsImpl userEntity = (UserDetailsImpl) authentication.getPrincipal();
        if (userEntity.getUserEntity().getRole().getName().equalsIgnoreCase("admin")) {
            response.sendRedirect("/admin");
        } else {
            response.sendRedirect("/user/home/page/1");
        }
    }
}

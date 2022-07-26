package com.example.ontapspring0205.security.config;

import com.example.ontapspring0205.security.oauth2.CustomOAuth2UserServiceImpl;
import com.example.ontapspring0205.security.oauth2.OAuth2LoginSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    CustomOAuth2UserServiceImpl customOAuth2UserService;
    @Autowired
    OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;
    @Autowired
    AuthenSuccessHandlerLogin successHandlerLogin;

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/forgot-password", "/static/**", "/templates/**", "/add-user", "/save-user", "/signIn").permitAll()
                .antMatchers(HttpMethod.POST, "/send-email-thymeleaf").permitAll()
                .antMatchers("/", "/login").permitAll()
                .antMatchers("/admin/**", "/product/**").hasAnyAuthority("admin")
                .antMatchers("/user/**", "/user/home").hasAnyAuthority("user")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .usernameParameter("email")
                .passwordParameter("password")
                .successHandler(successHandlerLogin)
                .permitAll()
                .failureHandler(
                        (req, res, auth)->{
                            req.getRequestDispatcher("/login").forward(req,res);
                        }
                )
                .and()
                .oauth2Login()
                .loginPage("/login")
                .userInfoEndpoint()
                .userService(customOAuth2UserService)
                .and()
                .successHandler(oAuth2LoginSuccessHandler)
                .and()
                .logout()
                .permitAll()
                .and()
                .exceptionHandling()
                .accessDeniedPage("/403");
        ;
    }
}

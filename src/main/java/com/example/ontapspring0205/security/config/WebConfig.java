package com.example.ontapspring0205.security.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    // giong voi controller dung de chuyen huowng toi view khi truy cap vao duong dan /403 hoac /login
//    @Override
//    public void addViewControllers(ViewControllerRegistry registry) {
//        registry.addViewController("/403").setViewName("403");
//        registry.addViewController("/login").setViewName("login");
//    }

    @Value("${upload.path}")
    private String path;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        Path userUploadDir = Paths.get("./src/main/resources/static/images");
//        String userUpLoadPath = userUploadDir.toFile().getAbsolutePath();
//        registry.addResourceHandler("/images/**").addResourceLocations("file://" + userUpLoadPath + "/");
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/templates/**").addResourceLocations("classpath:/templates/");
        registry.addResourceHandler("/images/**").addResourceLocations("file:///"+path);
    }

}

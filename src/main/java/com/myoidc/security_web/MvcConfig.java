package com.myoidc.security_web;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/protected").setViewName("protected");
        registry.addViewController("/unprotected").setViewName("unprotected");
        registry.addViewController("/login").setViewName("login_page");
    }

}
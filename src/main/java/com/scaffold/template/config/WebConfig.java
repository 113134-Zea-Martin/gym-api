package com.scaffold.template.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Value("${frontend.url}")
    private String frontEndUrl;

    @Value("${backend.url}")
    private String backEndUrl;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        String localUrl = "http://localhost:4200";
        registry.addMapping("/**")
                .allowedOrigins(frontEndUrl, localUrl, backEndUrl, "**")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .exposedHeaders("Access-Control-Allow-Origin", "Authorization")
                .allowCredentials(true);
    }
}

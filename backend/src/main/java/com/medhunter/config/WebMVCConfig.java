package com.medhunter.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;
//created for CROS
@Configuration
public class WebMVCConfig  implements WebMvcConfigurer {

    @Value("#{'${core.allowed-origins}'.split(',')}")
    private List<String> allowedOrigin ;

    @Value("#{'${core.allowed-methods}'.split(',')}")
    private List<String> allowedMethods ;
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        final CorsRegistration corsRegistration = registry.addMapping("/api/**") ;
        allowedOrigin.forEach(corsRegistration::allowedOrigins);
        allowedMethods.forEach(corsRegistration::allowedMethods);

    }
}

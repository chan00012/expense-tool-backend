package com.ibm.expensetool.core.configuration;

import com.ibm.expensetool.core.interceptor.SessionInterceptor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@Configuration
public class InterceptorConfiguration implements WebMvcConfigurer {

    @Value("#{'${jwt.endpoint.list}'.split(',')}")
    private List<String> sessionEndpoints;

    @Autowired
    private SessionInterceptor sessionInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> endpoints = new ArrayList<>(sessionEndpoints);

        log.info("|SESSION ENDPOINTS SET| - {}", endpoints);

        registry.addInterceptor(sessionInterceptor).addPathPatterns(endpoints);

    }
}

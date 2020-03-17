package com.ibm.expensetool.core.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.Map;

@Data
@Configuration
@PropertySource("classpath:error.properties")
@ConfigurationProperties("error")
public class ExceptionProperties {

    private Map<String, String> rc;
    private String genericSpiel;
    private String genericCode;
    private String genericSystemCode;

}
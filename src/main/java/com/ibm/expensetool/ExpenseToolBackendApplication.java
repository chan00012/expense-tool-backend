package com.ibm.expensetool;

import com.ibm.expensetool.core.service.ReportService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@Log4j2
@SpringBootApplication
public class ExpenseToolBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExpenseToolBackendApplication.class, args);
    }

    @Bean
    public CommandLineRunner run() {
        return args -> {

        };
    }

}

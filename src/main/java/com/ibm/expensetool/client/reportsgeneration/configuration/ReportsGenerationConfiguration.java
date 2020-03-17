package com.ibm.expensetool.client.reportsgeneration.configuration;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.core.GenericSelector;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.file.FileReadingMessageSource;
import org.springframework.integration.file.FileWritingMessageHandler;
import org.springframework.integration.file.support.FileExistsMode;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;

import java.io.File;

@Log4j2
@Configuration
@EnableIntegration
public class ReportsGenerationConfiguration {

    @Value("${report.source}")
    private String sourceDirectory;

    @Value("${report.target}")
    private String targetDirectory;

    @Bean
    private MessageSource<File> sourceDirectory(){
        FileReadingMessageSource messageSource = new FileReadingMessageSource();
        messageSource.setDirectory(new File(sourceDirectory));
        messageSource.setAutoCreateDirectory(true);
        return messageSource;
    }

    @Bean
    private MessageHandler targetDirectory(){
        FileWritingMessageHandler handler = new FileWritingMessageHandler(new File(targetDirectory));
        handler.setFileExistsMode(FileExistsMode.REPLACE);
        handler.setExpectReply(false);
        return handler;
    }

    @Bean
    private GenericSelector<File> filterReport(){
        return file -> file.getName().endsWith(".txt");
    }

    @Bean
    public IntegrationFlow fileMover(){
        return IntegrationFlows.from(sourceDirectory(),c -> c.poller(Pollers.fixedDelay(15000)))
                                .filter(filterReport())
                                .handle(targetDirectory())
                                .get();
    }
}

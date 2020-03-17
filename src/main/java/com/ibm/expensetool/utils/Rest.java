package com.ibm.expensetool.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibm.expensetool.client.reportsgeneration.dto.ReportForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;

public class Rest {

    private static Logger logger = LoggerFactory.getLogger(Rest.class);

    public static String toJsonString(Object object) {
        ObjectMapper objectMapper = new ObjectMapper();

        String jsonFormat = "";
        try {
            jsonFormat = objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            logger.info("JSON PARSING ERROR: {} object not parseable", object.getClass().getName());
            e.printStackTrace();
        }

        return jsonFormat;
    }

    public static boolean isJsonType(String contentType) {
        return (MediaType.APPLICATION_JSON_VALUE.equals(contentType)) ? true : false;
    }

    public static boolean isXmlType(String contentType) {
        return (MediaType.APPLICATION_XML_VALUE.equals(contentType)) ? true : false;
    }

    @Bean
    ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    public static String serializeReport(ReportForm reportForm) {
        return reportForm.getUserId() + "|" + reportForm.getName() + "|" + reportForm.getEmail() + "|" + reportForm.getBookType() + "|" + reportForm.getAmount() + "|" + reportForm.getDateRecorded();

    }

}
package com.ibm.expensetool.core.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.ibm.expensetool.utils.Rest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExceptionResponse {

    private String systemCode;
    private String code;
    private String message;
    private List<String> messages;

    @JsonIgnore
    private HttpStatus status;

    public ExceptionResponse(String message) {
        this.message = message;
    }

    public ExceptionResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public ExceptionResponse(String code, String message, HttpStatus status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }

    @Override
    public String toString() {
        return Rest.toJsonString(this);
    }
}

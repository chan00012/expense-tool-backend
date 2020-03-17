package com.ibm.expensetool.core.exception;

import com.ibm.expensetool.core.configuration.ExceptionProperties;
import com.ibm.expensetool.core.dto.response.ExceptionResponse;
import com.ibm.expensetool.utils.ApplicationException;
import io.jsonwebtoken.JwtException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;

@Log4j2
@ControllerAdvice
public class ExpenseToolExceptionHandler {

    ExceptionProperties errProp;
    private Map<String, String> rcMap;

    @Autowired
    private ExpenseToolExceptionHandler(ExceptionProperties errProp) {
        this.rcMap = errProp.getRc();
        this.errProp = errProp;
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleException(Exception exception, HttpServletRequest request) {

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        String systemCode = errProp.getGenericSystemCode();
        String code = errProp.getGenericCode();
        String message = errProp.getGenericSpiel();

        Class<?> exceptionClass = exception.getClass();
        String description = exception.getMessage();

        if (exception instanceof ApplicationException) {

            ApplicationException appException = (ApplicationException) exception;

            if (appException.hasCode()) {
                code = appException.getCode();


                if (appException.isWithMapping()) {
                    message = rcMap.get(MessageFormat.format("{0}.{1}", appException.getSystemCode(), appException.getCode()));
                    message = ofNullable(message).orElse(rcMap.get(appException.getSystemCode()));
                } else {
                    message = appException.getMessage();
                }
            } else {
                code = appException.getSystemCode();
                message = rcMap.get(appException.getSystemCode());
                description = message;

                if (!appException.isWithMapping()) {
                    message = appException.getMessage();
                }
            }

            description = ofNullable(description).orElse(rcMap.get(appException.getSystemCode()));

            systemCode = appException.getSystemCode();
            status = appException.getStatus();

        }

        ExceptionResponse exceptionResponse = ExceptionResponse.builder().systemCode(systemCode).message(message).build();

        log.error("|{}| SERVER RESPONSE - ERR DESC:{} , PAYLOAD: {}", exception.getClass().getSimpleName(), exception.getMessage(), message);
        return new ResponseEntity<>(exceptionResponse, status);


    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleException(MethodArgumentNotValidException exception, HttpServletRequest request) {

        HttpStatus status = HttpStatus.BAD_REQUEST;
        String systemCode = errProp.getGenericSystemCode();
        String code = errProp.getGenericCode();
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        List<String> messages = fieldErrors.stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());


        ExceptionResponse exceptionResponse = ExceptionResponse.builder().systemCode(systemCode).messages(messages).build();

        log.error("|{}| SERVER RESPONSE - ERR DESC:{} , PAYLOAD: {}", exception.getClass().getSimpleName(), fieldErrors, messages);
        return new ResponseEntity<>(exceptionResponse, status);
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ExceptionResponse> handleException(JwtException exception, HttpServletRequest request) {

        HttpStatus status = HttpStatus.UNAUTHORIZED;
        String systemCode = errProp.getGenericSystemCode();
        String code = errProp.getGenericCode();
        String message = exception.getMessage();

        ExceptionResponse exceptionResponse = ExceptionResponse.builder().systemCode(systemCode).message(message).build();

        log.error("|{}| SERVER RESPONSE - ERR DESC:{} , PAYLOAD: {}", exception.getClass().getSimpleName(), message, message);
        return new ResponseEntity<>(exceptionResponse, status);
    }


}
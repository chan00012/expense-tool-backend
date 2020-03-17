package com.ibm.expensetool.utils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;


@Getter
@Setter
public class ApplicationException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Code to determine which system caused the error. <br>
     * Note: Assign a default system code for every subclass that extends this class.
     */
    protected String systemCode;

    /*
     * Code of the specific error.
     * The reason why this is string is because some system returns String format code.
     */
    protected String code;

    /*
     * Message of the specific error.
     * Note: If you want the message to look for error.properies file, set withMapping to true.
     */
    protected String message;

    /**
     * Flagging if the message will be mapped via error.properties<br>
     * Make a default value for every end subclass that extends this class.
     * <p>
     * Default value: <i>false</i>
     */
    @JsonIgnore
    protected boolean withMapping = false;

    /*
     * Optional HTTP Status Code.
     * Default value: <i>HttpStatus.INTERNAL_SERVER_ERROR</i>
     *
     */
    protected HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

    public ApplicationException(String systemCode, HttpStatus status) {
        this.systemCode = systemCode;
        this.status = status;
    }

    public ApplicationException(String systemCode, boolean withMapping, HttpStatus status) {
        this.systemCode = systemCode;
        this.withMapping = withMapping;
        this.status = status;
    }

    public ApplicationException(String systemCode, String message, boolean withMapping, HttpStatus status) {
        this.systemCode = systemCode;
        this.message = message;
        this.withMapping = withMapping;
        this.status = status;
    }

    public ApplicationException(String systemCode, String code, String message, boolean withMapping, HttpStatus status) {
        this.systemCode = systemCode;
        this.code = code;
        this.message = message;
        this.withMapping = withMapping;
        this.status = status;
    }

    public boolean hasCode() {
        return this.code != null;
    }
}
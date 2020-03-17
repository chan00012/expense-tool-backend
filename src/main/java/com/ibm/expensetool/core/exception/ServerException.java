package com.ibm.expensetool.core.exception;

import com.ibm.expensetool.utils.ApplicationException;
import org.springframework.http.HttpStatus;


public class ServerException extends ApplicationException {

    private static final long serialVersionUID = 1L;

    private static final String CLIENT_GENERIC_CODE = "4000";

    private static final HttpStatus CLIENT_ERROR_STATUS = HttpStatus.BAD_REQUEST;

    //	######################################	//
    //	__           ,           ,          	//
    //	/  ` _ ._  __-+-._.. . _.-+- _ ._. __	//
    //	\__.(_)[ )_)  | [  (_|(_. | (_)[  _) 	//
    //	                                     	//
    //	######################################	//

    //WIH DEFAULT STATUS
    public ServerException() {
        super(CLIENT_GENERIC_CODE, CLIENT_ERROR_STATUS);
    }

    public ServerException(String genericCode) {
        super(genericCode, true, CLIENT_ERROR_STATUS);
    }

    public ServerException(String genericCode, String message) {
        super(genericCode, message, false, CLIENT_ERROR_STATUS);
    }

    public ServerException(String genericCode, String code, String message) {
        super(genericCode, code, message, false, CLIENT_ERROR_STATUS);
    }

    //WITH CUSTOM STATUS
    public ServerException(String genericCode, HttpStatus status) {
        super(genericCode, true, status);
    }

    public ServerException(String genericCode, String message, HttpStatus status) {
        super(genericCode, message, false, status);
    }

    public ServerException(String genericCode, String code, String message, HttpStatus status) {
        super(genericCode, code, message, false, status);
    }

    //	###############################	//
    //	.___             ,           	//
    //	[__ \./ _. _ ._ -+-* _ ._  __	//
    //	[___/'\(_.(/,[_) | |(_)[ )_) 	//
    //	             |               	//
    //	###############################	//

    public static final class ExistingUserException extends ServerException {

        private static final long serialVersionUID = 1L;
        private static final String SYSTEM_CODE = "5000";
        private static final HttpStatus status = HttpStatus.CONFLICT;

        public ExistingUserException() {
            super(SYSTEM_CODE, status);
        }

        public ExistingUserException(String message) {
            super(SYSTEM_CODE, message);
        }

        public ExistingUserException(String code, String message, boolean withMapping) {
            super(SYSTEM_CODE, code, message);
        }
    }

    public static final class NotExistingUserException extends ServerException {

        private static final long serialVersionUID = 1L;
        private static final String SYSTEM_CODE = "5001";
        private static final HttpStatus status = HttpStatus.CONFLICT;

        public NotExistingUserException() {
            super(SYSTEM_CODE, status);
        }

        public NotExistingUserException(String message) {
            super(SYSTEM_CODE, message);
        }

        public NotExistingUserException(String code, String message, boolean withMapping) {
            super(SYSTEM_CODE, code, message);
        }
    }

    public static final class InvalidCredentialsException extends ServerException {

        private static final long serialVersionUID = 1L;
        private static final String SYSTEM_CODE = "5002";
        private static final HttpStatus status = HttpStatus.CONFLICT;

        public InvalidCredentialsException() {
            super(SYSTEM_CODE, status);
        }

        public InvalidCredentialsException(String message) {
            super(SYSTEM_CODE, message);
        }

        public InvalidCredentialsException(String code, String message, boolean withMapping) {
            super(SYSTEM_CODE, code, message);
        }
    }
}
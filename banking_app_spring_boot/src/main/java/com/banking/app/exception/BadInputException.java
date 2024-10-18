package com.banking.app.exception;

import org.springframework.http.HttpStatus;

public class BadInputException extends RuntimeException {

    private HttpStatus httpStatus;
    private String message;

    public BadInputException(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

}

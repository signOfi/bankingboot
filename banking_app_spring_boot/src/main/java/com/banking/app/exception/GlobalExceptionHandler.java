package com.banking.app.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import java.nio.file.AccessDeniedException;
import java.util.Date;


@ControllerAdvice
public class GlobalExceptionHandler {

    private ResponseEntity<ErrorDetails> createErrorResponse(Exception exception,
                                                             WebRequest webRequest,
                                                             HttpStatus status) {
        ErrorDetails errorDetails = new ErrorDetails(
                new Date(),
                exception.getMessage(),
                webRequest.getDescription(false)
        );
        return new ResponseEntity<>(errorDetails, status);
    }


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFoundException exception,
                                                                        WebRequest webRequest) {
        return createErrorResponse(exception, webRequest, HttpStatus.NOT_FOUND);
    }

    /* Handle objects with mismatch such as authorizationId not matching one to many or many to one */
    @ExceptionHandler(BankingException.class)
    public ResponseEntity<ErrorDetails> handleBankingException(BankingException exception,
                                                               WebRequest webRequest) {
        return createErrorResponse(exception, webRequest, HttpStatus.BAD_REQUEST);
    }

    /* Handles bad input */
    @ExceptionHandler(BadInputException.class)
    public ResponseEntity<ErrorDetails> handleBadInput(ResourceNotFoundException exception,
                                                       WebRequest webRequest) {
        return createErrorResponse(exception, webRequest, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorDetails> handleNotEnoughFields(DataIntegrityViolationException exception,
                                                        WebRequest webRequest) {
        return createErrorResponse(exception, webRequest, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorDetails> handleMethodMismatch (MethodArgumentTypeMismatchException exception,
                                                              WebRequest webRequest) {
        return createErrorResponse(exception, webRequest, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorDetails> handleMethodMismatch (HttpMessageNotReadableException exception,
                                                              WebRequest webRequest) {
        return createErrorResponse(exception, webRequest, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorDetails> handleAccessDenied(AccessDeniedException exception,
                                                           WebRequest webRequest) {
        return createErrorResponse(exception, webRequest, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDetails> methodArgumentNotValid(MethodArgumentNotValidException exception,
                                                           WebRequest webRequest) {
        return createErrorResponse(exception, webRequest, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> generalExceptionHandler(Exception exception,
                                                                WebRequest webRequest) {
        return createErrorResponse(exception, webRequest, HttpStatus.NOT_FOUND);
    }

}

package com.proxyseller.exception.handler;

import com.proxyseller.dto.ApiError;
import com.proxyseller.exception.PostNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
public class CustomerExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ApiError> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                    WebRequest request) {
        var message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .findFirst()
                .map(FieldError::getDefaultMessage)
                .orElse("Validation error");
        return new ResponseEntity<>(new ApiError(now(), message, BAD_REQUEST), BAD_REQUEST);
    }

    @ExceptionHandler(PostNotFoundException.class)
    protected ResponseEntity<ApiError> handlePostNotFound(PostNotFoundException ex,
                                                          WebRequest request) {
        return new ResponseEntity<>(new ApiError(now(), ex.getMessage(), NOT_FOUND), NOT_FOUND);
    }
}

package com.proxyseller.exception.handler;

import com.proxyseller.dto.ApiError;
import com.proxyseller.exception.PostNotFoundException;
import com.proxyseller.exception.UserAlreadyExistsException;
import com.proxyseller.exception.UserNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.*;

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

    @ExceptionHandler({PostNotFoundException.class, UserNotFoundException.class})
    protected ResponseEntity<ApiError> handleNotFound(Exception ex, WebRequest request) {
        return new ResponseEntity<>(new ApiError(now(), ex.getMessage(), NOT_FOUND), NOT_FOUND);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    protected ResponseEntity<ApiError> handleAlreadyExist(Exception ex, WebRequest request) {
        return new ResponseEntity<>(new ApiError(now(), ex.getMessage(), CONFLICT), CONFLICT);
    }
}

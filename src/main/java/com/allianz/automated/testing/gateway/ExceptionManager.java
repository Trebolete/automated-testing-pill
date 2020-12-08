package com.allianz.automated.testing.gateway;

import com.allianz.automated.testing.model.api.ApiError;
import com.allianz.automated.testing.model.exception.NotFoundException;
import com.allianz.automated.testing.model.exception.PolicyNotValidException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionManager {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiError> handle(NotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ApiError(HttpStatus.NOT_FOUND, exception.getMessage()));
    }

    @ExceptionHandler(PolicyNotValidException.class)
    public ResponseEntity<ApiError> handle(PolicyNotValidException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ApiError(HttpStatus.BAD_REQUEST, exception.getMessage()));
    }

}

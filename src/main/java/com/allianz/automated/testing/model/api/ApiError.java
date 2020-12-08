package com.allianz.automated.testing.model.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public class ApiError {

    private final HttpStatus httpStatus;
    private final String description;

}

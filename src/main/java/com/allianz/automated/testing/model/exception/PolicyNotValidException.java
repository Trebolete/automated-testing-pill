package com.allianz.automated.testing.model.exception;

public class PolicyNotValidException extends RuntimeException {

    public PolicyNotValidException(String message) {
        super(message);
    }

}
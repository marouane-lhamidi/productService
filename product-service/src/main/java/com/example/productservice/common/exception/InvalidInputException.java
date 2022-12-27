package com.example.productservice.common.exception;

import org.springframework.http.HttpStatus;

public class InvalidInputException extends BaseException {
    public InvalidInputException(String message) {
        super(HttpStatus.BAD_REQUEST.value(), message);
    }
}

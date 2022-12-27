package com.example.productservice.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class HttpUnauthorizedException extends BaseException {
    private static final long serialVersionUID = 1L;

    public HttpUnauthorizedException(String message) {
        super(HttpStatus.UNAUTHORIZED.value(), message);
    }

    public static HttpUnauthorizedException unauthorized() {
        return new HttpUnauthorizedException("unauthorized");
    }
}

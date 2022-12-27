package com.example.productservice.common.exception;

import org.springframework.http.HttpHeaders;

public class BaseException extends RuntimeException {
    private static final long serialVersionUID = 7635825615926298648L;
    private int status;
    private String message;
    private HttpHeaders extraHeaders;

    public BaseException(int status, String message) {
        super(message);
        this.status = status;
        this.message = message;
    }

    public BaseException() {
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HttpHeaders getExtraHeaders() {
        return this.extraHeaders;
    }

    public void setExtraHeaders(HttpHeaders extraHeaders) {
        this.extraHeaders = extraHeaders;
    }

    public static BaseException raiseNotFoundException(String msg) {
        return new BaseException(404, msg);
    }

}
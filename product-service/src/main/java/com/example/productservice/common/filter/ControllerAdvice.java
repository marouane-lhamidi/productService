package com.example.productservice.common.filter;

import com.example.productservice.common.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@RestControllerAdvice
public class ControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ Exception.class })
    public ResponseEntity<ResponseExceptionModel> handleException(Exception e) {

        var model = new ResponseExceptionModel();

        if(e instanceof InvalidInputException
                || e instanceof HttpUnauthorizedException
                || e instanceof InvalidOtpException){

            var o = (BaseException) e;
            model.setMessage(e.getMessage());
            model.setStatus(o.getStatus());
        }


        return new ResponseEntity<ResponseExceptionModel>(model, HttpStatus.valueOf(model.getStatus()));
    }

}

package com.kingcong.branch.exercise.handler;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { HttpClientErrorException.class, HttpServerErrorException.class })
    protected ResponseEntity<Object> handleHttpClientError(
            RuntimeException ex, WebRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        if( ex instanceof HttpClientErrorException) {
            status = HttpStatus.valueOf((((HttpClientErrorException) ex).getStatusCode().value()));
        } else if ( ex.getCause() instanceof HttpClientErrorException){
            status = HttpStatus.valueOf((((HttpClientErrorException) ex.getCause()).getStatusCode().value()));
        }
        return handleExceptionInternal(ex, status.getReasonPhrase(), new HttpHeaders(), status, request);
    }
}

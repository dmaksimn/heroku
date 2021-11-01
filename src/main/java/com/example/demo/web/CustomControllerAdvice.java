package com.example.demo.web;

import com.example.demo.domain.dto.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@ResponseBody
public class CustomControllerAdvice {
    private final static Logger logger = LoggerFactory.getLogger(CustomControllerAdvice.class);
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse errorResponse(MethodArgumentNotValidException ex) {
        logger.warn("Cannot process request", ex);
        return new ErrorResponse("Request contains invalid arguments", 400);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse errorResponse(EmptyResultDataAccessException ex) {
        return new ErrorResponse("Cannot find post", 404);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse errorResponse(Exception ex) {
        return new ErrorResponse(ex.getMessage(), 500);
    }
}

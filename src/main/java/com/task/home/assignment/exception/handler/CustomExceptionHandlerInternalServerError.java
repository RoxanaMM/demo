package com.task.home.assignment.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class CustomExceptionHandlerInternalServerError extends Exception {
    public CustomExceptionHandlerInternalServerError() {
        super();
    }

    public CustomExceptionHandlerInternalServerError(String message, Throwable cause) {
        super(message, cause);
    }

    public CustomExceptionHandlerInternalServerError(String message) {
        super(message);
    }

    public CustomExceptionHandlerInternalServerError(Throwable cause) {
        super(cause);
    }
}
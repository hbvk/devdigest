package com.hbvk.devdigest.exception.handler;

import com.hbvk.devdigest.exception.SampleException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class AppExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger exceptionLogger = LogManager.getLogger(AppExceptionHandler.class);

    @ExceptionHandler({RuntimeException.class, SampleException.class})
    public ResponseEntity<String> handleRuntimeException(Exception e) {
        exceptionLogger.error("error: {}", e.getClass().getSimpleName(), e);
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

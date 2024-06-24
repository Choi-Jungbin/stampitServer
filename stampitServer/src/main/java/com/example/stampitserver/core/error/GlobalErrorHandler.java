package com.example.stampitserver.core.error;

import com.example.stampitserver.core.error.exception.OutOfDate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalErrorHandler {

    @ExceptionHandler(OutOfDate.class)
    public ResponseEntity<?> outOfDate(OutOfDate e){
        return new ResponseEntity<>(e.body(), e.status());
    }
}

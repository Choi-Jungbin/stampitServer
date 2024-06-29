package com.example.stampitserver.core.error;

import com.example.stampitserver.core.error.exception.NotFondEnumException;
import com.example.stampitserver.core.error.exception.OutOfDateException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalErrorHandler {

    @ExceptionHandler(OutOfDateException.class)
    public ResponseEntity<?> outOfDate(OutOfDateException e){
        return new ResponseEntity<>(e.body(), e.status());
    }

    @ExceptionHandler(NotFondEnumException.class)
    public ResponseEntity<?> notFoundEnum(NotFondEnumException e){
        System.out.println(e.getMessage());
        return new ResponseEntity<>(e.body(), e.status());
    }
}

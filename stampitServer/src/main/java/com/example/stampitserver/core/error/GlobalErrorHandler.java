package com.example.stampitserver.core.error;

import com.example.stampitserver.core.error.exception.NotFondEnumException;
import com.example.stampitserver.core.error.exception.NotFoundException;
import com.example.stampitserver.core.error.exception.OutOfDateException;
import com.example.stampitserver.core.utils.ApiUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.net.MalformedURLException;

@ControllerAdvice
public class GlobalErrorHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> NotFoundException(NotFoundException e){
        ApiUtils.ApiResult<?> apiResult = ApiUtils.error("Not Found", e.getMessage(), e.status());

        return new ResponseEntity<>(apiResult, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MalformedURLException.class)
    public ResponseEntity<?> MalformedURLException(MalformedURLException e){
        ApiUtils.ApiResult<?> apiResult = ApiUtils.error("Bad Request", e.getMessage(), HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(apiResult, HttpStatus.BAD_REQUEST);
    }
}

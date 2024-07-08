package com.example.stampitserver.core.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

public class ApiUtils {

    @Getter
    @Setter
    @RequiredArgsConstructor
    public static class ApiError{
        private final String code;
        private final String details;
    }

    @Getter
    @Setter
    @RequiredArgsConstructor
    public static class ApiResult<T>{
        private final int status;
        private final String message;
        private final ApiError error;
        private final T data;
    }

    public static <T> ApiResult<T> success(T data, String message){
        return new ApiResult<>(HttpStatus.OK.value(), message, null, data);
    }

    public static ApiResult<?> error(String code, String details, HttpStatus status) {
        return new ApiResult<>(status.value(), "Error", new ApiError(code, details), null);
    }
}

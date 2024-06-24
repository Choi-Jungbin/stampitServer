package com.example.stampitserver.core.utils;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

public class ApiUtils {

    @AllArgsConstructor
    public static class ApiError{
        private final String message;
        private final int status;
    }

    @AllArgsConstructor
    public static class ApiResult<T>{
        private final boolean success;
        private final T response;
        private final ApiError error;
    }

    public static <T> ApiResult<T> success(T response){
        return new ApiResult<>(true, response, null);
    }

    public static ApiResult<?> error(String message, HttpStatus status){
        return new ApiResult<>(false, null, new ApiError(message, status.value()));
    }
}

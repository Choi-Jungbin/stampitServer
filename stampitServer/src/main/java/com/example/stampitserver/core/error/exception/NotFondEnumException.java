package com.example.stampitserver.core.error.exception;

import com.example.stampitserver.core.utils.ApiUtils;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class NotFondEnumException extends RuntimeException{

    public NotFondEnumException(String message){ super(message);}

    public ApiUtils.ApiResult<?> body(){return ApiUtils.error("Not Found Enum", getMessage(), HttpStatus.BAD_REQUEST);}

    public HttpStatus status(){return HttpStatus.BAD_REQUEST;}
}

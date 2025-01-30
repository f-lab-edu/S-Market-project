package com.flab.s_market.common.exception;

import java.util.Map;
import java.util.function.Consumer;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CustomException extends RuntimeException{

    private final ErrorCode errorCode;
    private Map<String, Object> parameters;
    private Consumer<String> logMethod;
    public String getMessage(){
        return errorCode.getMessage();
    }
    public CustomException(ErrorCode errorCode, Map<String, Object> parameters, Consumer<String> logMethod){
        this.errorCode = errorCode;
        this.parameters = parameters;
        this.logMethod = logMethod;
    }
}

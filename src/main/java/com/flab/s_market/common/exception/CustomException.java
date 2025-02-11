package com.flab.s_market.common.exception;

import java.util.Map;
import java.util.function.Consumer;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException{

    private final ErrorCode errorCode;
    private final Map<String, Object> parameters;
    private final Consumer<String> logMethod;
    private final Exception exception;
    // ex. 외부 라이브러리에서 i/o exception 발생함 -> customException으로 예외 던짐 -> 원본 exception 추가해서 던져줌

    public CustomException(ErrorCode errorCode, Map<String, Object> parameters, Consumer<String> logMethod){
        this(errorCode, parameters, logMethod, null);
    }

    public String getMessage(){
        return errorCode.getExternalMessage();
    }
    public CustomException(ErrorCode errorCode, Map<String, Object> parameters, Consumer<String> logMethod, Exception exception){
        this.errorCode = errorCode;
        this.parameters = parameters;
        this.logMethod = logMethod;
        this.exception = exception;
    }
}

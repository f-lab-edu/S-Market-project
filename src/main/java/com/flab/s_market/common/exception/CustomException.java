package com.flab.s_market.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CustomException extends RuntimeException{
    private final ErrorCode errorCode;

    public String getMessage(){
        return errorCode.getMessage();
    }
}

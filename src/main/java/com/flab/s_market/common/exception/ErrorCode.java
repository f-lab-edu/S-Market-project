package com.flab.s_market.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    EXIST_EMAIL("ACCOUNT-001", "이미 사용중인 이메일입니다. 다른 이메일을 작성해주세요.", HttpStatus.BAD_REQUEST);

    private final String code;
    private final String message;
    private final HttpStatus httpStatus;
}

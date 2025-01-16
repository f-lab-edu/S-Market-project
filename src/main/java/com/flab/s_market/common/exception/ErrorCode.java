package com.flab.s_market.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    EXIST_EMAIL("ACCOUNT-001", "이미 사용중인 이메일입니다. 다른 이메일을 작성해주세요.", HttpStatus.BAD_REQUEST),
    NOT_VALID_EMAIL_CODE("ACCOUNT-002", "인증코드가 틀립니다. 이메일을 확인해주세요.", HttpStatus.NOT_FOUND);

    private final String code;
    private final String message;
    private final HttpStatus httpStatus;
}

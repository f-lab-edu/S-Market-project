package com.flab.s_market.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    EXIST_EMAIL("ACCOUNT-001", "이미 사용중인 이메일입니다. 다른 이메일을 작성해주세요.", HttpStatus.BAD_REQUEST),
    NOT_VALID_EMAIL_CODE("ACCOUNT-002", "인증코드가 틀립니다. 이메일을 확인해주세요.", HttpStatus.NOT_FOUND),
    NOT_VALID_PASSWORD("ACCOUNT-03", "비밀번호와 재확인 비밀번호가 다릅니다. 비밀번호를 다시 확인해주세요.", HttpStatus.BAD_REQUEST),
    NOT_EXIST_TERM("ACCOUNT-04", "존재하지 않는 약관입니다. 약관 버전 또는 약관명을 다시 확인해주세요.", HttpStatus.NOT_FOUND),
    MAIL_SYSTEM_ERROR("ACCOUNT-05", "이메일 시스템 에러가 발생했습니다. 발송 이메일을 다시 확인해주세요.", HttpStatus.INTERNAL_SERVER_ERROR),
    ENCRYPTION_FAILED("ACCOUNT-06", "이메일 암호화에 실패하였습니다. 다시 시도해주세요.", HttpStatus.INTERNAL_SERVER_ERROR),
    DECRYPTION_FAILED("ACCOUNT-07", "이메일키 복호화에 실패하였습니다. 다시 시도해주세요.", HttpStatus.INTERNAL_SERVER_ERROR),
    NOT_ALL_AGREED_REQUIRED_TERMS("ACCOUNT-08", "필수 약관에 모두 동의해야합니다.", HttpStatus.BAD_REQUEST);

    private final String code;
    private final String message;
    private final HttpStatus httpStatus;
}

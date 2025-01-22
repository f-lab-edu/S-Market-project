package com.flab.s_market.common.exception;

import com.flab.s_market.common.entity.ApiResponse;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value={CustomException.class})
    public ResponseEntity<ApiResponse<?>> handleCustomException(CustomException e){
        log.error("handleCustomException() in GlobalExceptionHandler throw CustomException : {}", e.getMessage());
        return ResponseEntity
            .status(e.getErrorCode().getHttpStatus())
            .body(ApiResponse.createFail(e));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException e) {
        log.error("handleConstraintViolationException() in GlobalExceptionHandler throw ConstraintViolationException : {}", e.getMessage());
        String errorMessage = e.getConstraintViolations().iterator().next().getMessage();
        return new ResponseEntity<>(ApiResponse.createFailWithBindingResult(errorMessage),
            HttpStatus.BAD_REQUEST);
    }
}

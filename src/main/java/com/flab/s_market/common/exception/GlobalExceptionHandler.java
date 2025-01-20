package com.flab.s_market.common.exception;

import com.flab.s_market.common.entity.ApiResponse;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
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
    @ExceptionHandler(value={MethodArgumentNotValidException.class})
    public ResponseEntity<?> handleMethodArgumentException(MethodArgumentNotValidException e) {
        BindingResult result = e.getBindingResult();
        StringBuilder errMessage = new StringBuilder();

        for (FieldError error : result.getFieldErrors()) {
            errMessage.append(error.getDefaultMessage());
        }
        return new ResponseEntity<>(ApiResponse.createFailWithBindingResult(errMessage.toString()),
            HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException e) {
        String errorMessage = e.getConstraintViolations().iterator().next().getMessage();
        return new ResponseEntity<>(ApiResponse.createFailWithBindingResult(errorMessage),
            HttpStatus.BAD_REQUEST);
    }
}

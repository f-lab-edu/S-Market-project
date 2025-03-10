package com.flab.s_market.common.exception;

import com.flab.s_market.common.entity.ApiResponse;
import jakarta.validation.ConstraintViolationException;
import java.util.Map;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    // error response를 정확히 어떻게 바꿔야할지 고민중
    @Order(1)
    @ExceptionHandler(value={CustomException.class})
    public ResponseEntity<ApiResponse<?>> handleCustomException(CustomException e){
        Map<String, Object> parameters = e.getParameters();
        if(e.getLogMethod() != null){
            e.getLogMethod().accept("CustomException occured : parameters - " + parameters);
        }
        return ResponseEntity
            .status(e.getErrorCode().getHttpStatus())
            .body(ApiResponse.createFail(e));
    }
    @Order(2)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponse<?>> handleConstraintViolationException(ConstraintViolationException e) {
        String errorMessage = ExceptionUtils.getFullStackTrace(e);
        log.error("ConstraintViolationException occured : getFullStackTrace - " + errorMessage);

        return new ResponseEntity<>(ApiResponse.createFailWithErrorMessage(errorMessage), HttpStatus.BAD_REQUEST);
    }

    @Order(3)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiResponse<?>> handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        String errorMessage = ExceptionUtils.getFullStackTrace(e);
        log.info("MissingServletRequestParameterException occured : getFullStackTrace - " + errorMessage);

        return new ResponseEntity<>(ApiResponse.createFailWithErrorMessage(errorMessage), HttpStatus.BAD_REQUEST);
    }

    @Order(99)
    @ExceptionHandler(value={Exception.class})
    public ResponseEntity<ApiResponse<?>> handleException(Exception e){
        String errorMessage = ExceptionUtils.getFullStackTrace(e);
        log.error("Exception occured : getFullStackTrace - " + errorMessage);
        // external error 어떻게 활용..??
         // 9999
        return new ResponseEntity<>(ApiResponse.createFailWithErrorMessage(errorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

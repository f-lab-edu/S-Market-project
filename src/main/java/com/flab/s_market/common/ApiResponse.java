package com.flab.s_market.common;

import com.flab.s_market.common.exception.CustomException;
import jakarta.annotation.Nullable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ApiResponse <T>{
    private static final String SUCCESS_CODE = "0";

    // @Valid에 의해 발생한 에러는 code를 어떻게 처리해야할지?
    private static final String VALID_FAIL_CODE = "1";

    private String code;
    @Nullable
    private T data;

    public static <T> ApiResponse<T> createSuccess(final T data){
        return new ApiResponse<>(SUCCESS_CODE, data);
    }
    public static ApiResponse<?> createSuccessWithNoContent(){
        return new ApiResponse<>(SUCCESS_CODE, null);
    }
    public static ApiResponse<?> createFail(final CustomException e){
        return new ApiResponse<>(e.getErrorCode().getCode(), e.getMessage());
    }

    public static ApiResponse<?> createFailWithBindingResult(final String errorMessage){
        return new ApiResponse<>(VALID_FAIL_CODE, errorMessage);
    }
}

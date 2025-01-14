package com.flab.s_market.common;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ApiResponse <T>{
    private static final String SUCCESS_CODE = "0";

    private String code;
    private T data;
    public static <T> ApiResponse<T> createSuccess(T data){
        return new ApiResponse<>(SUCCESS_CODE, data);
    }
    public static ApiResponse<?> createSuccessWithNoContent(){
        return new ApiResponse<>(SUCCESS_CODE, null);
    }
}

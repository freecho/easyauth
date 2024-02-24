package com.easyauth.common.result;

import com.easyauth.common.constant.CodeConstant;
import com.easyauth.common.constant.MessageConstant;

public class Result<T> {
    private long code;
    private String message;
    private T data;

    protected Result(long code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> Result<T> success() {
        return new Result<T>(CodeConstant.SUCCESS, MessageConstant.SUCCESS, null);
    }

    public static <T> Result<T> success(T data) {
        return new Result<T>(CodeConstant.SUCCESS, MessageConstant.SUCCESS, data);
    }

    public static <T> Result<T> success(String message, T data) {
        return new Result<T>(CodeConstant.SUCCESS, message, data);
    }

    public static <T> Result<T> failed(String message) {
        return new Result<T>(CodeConstant.FAILED, message, null);
    }


}

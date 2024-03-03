package com.easyauth.common.result;

import com.easyauth.common.constant.CodeConstant;
import com.easyauth.common.constant.MessageConstant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> implements Serializable {
    private Integer code;
    private String message;
    private T data;


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

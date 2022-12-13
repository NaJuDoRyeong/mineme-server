package com.mineme.server.common;

import lombok.Getter;

@Getter
public class ResponseSuccessDto<T> extends ResponseDto {
    private T data;

    public ResponseSuccessDto(Boolean isSuccess, Integer code, String message, T data) {
        super(isSuccess, code, message);
        this.data = data;
    }
}

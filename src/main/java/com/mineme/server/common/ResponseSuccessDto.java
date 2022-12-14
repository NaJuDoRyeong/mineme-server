package com.mineme.server.common;

import lombok.Getter;

@Getter
public class ResponseSuccessDto<T> extends ResponseDto {
    private T data;

    public ResponseSuccessDto(Integer code, String message, T data) {
        super(true, code, message);
        this.data = data;
    }
}

package com.mineme.server.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class ResponseDto<T> {

    private Boolean isSuccess;
    private Integer code;
    private String message;
    private T data;
}

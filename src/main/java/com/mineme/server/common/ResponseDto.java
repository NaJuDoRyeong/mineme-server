package com.mineme.server.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


@AllArgsConstructor
@Builder
@Getter
public class ResponseDto {
    private Boolean isSuccess;
    private Integer code;
    private String message;
}

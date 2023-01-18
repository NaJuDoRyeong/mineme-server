package com.mineme.server.common.dto;

import com.mineme.server.common.enums.ErrorCode;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class ExceptionDto {
	private final int code;
	private final String message;

	public ExceptionDto(ErrorCode errorCode) {
		this.code = errorCode.getCode();
		this.message = errorCode.getMessage();
	}
}

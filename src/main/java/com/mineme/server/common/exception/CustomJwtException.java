package com.mineme.server.common.exception;

import com.mineme.server.common.enums.ErrorCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CustomJwtException extends RuntimeException {
	private final ErrorCode errorCode;
}

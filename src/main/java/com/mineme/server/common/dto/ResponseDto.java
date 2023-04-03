package com.mineme.server.common.dto;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;

import com.mineme.server.common.enums.ErrorCode;
import com.mineme.server.common.exception.CustomException;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class ResponseDto<T> {
	private final Boolean success;
	private final T data;
	private ExceptionDto error;

	public ResponseDto(@Nullable T data) {
		this.success = true;
		this.data = data;
		this.error = null;
	}

	public static ResponseDto<Object> nullableResponseDto() {
		return new ResponseDto<>(null);
	}

	public static ResponseEntity<ResponseDto<Object>> createdNullableResponseDto() {
		return ResponseEntity.status(HttpStatus.CREATED)
			.body(nullableResponseDto());
	}

	// 사용자 정의 Exception 에 따른 ExceptionDto 리턴
	public static ResponseEntity<Object> toResponseEntity(CustomException e) {
		return ResponseEntity.status(e.getErrorCode().getHttpStatus())
			.body(ResponseDto.builder()
				.success(false)
				.data(null)
				.error(new ExceptionDto(e.getErrorCode())).build());
	}

	// 그 외 Exception 에 따른 ExceptionDto 리턴
	public static ResponseEntity<Object> toResponseEntity(Exception e) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
			.body(ResponseDto.builder()
				.success(false)
				.data(null)
				.error(new ExceptionDto(ErrorCode.SERVER_ERROR)).build());
	}
}

package com.mineme.server.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {

	/* Invalid Request */
	TOKEN_EXPIRED(4001, HttpStatus.UNAUTHORIZED, "Expired Token."),
	INVALID_IMAGE_FORMAT(4002, HttpStatus.BAD_REQUEST, "Invalid Image format."),
	INVALID_TOKEN(4003, HttpStatus.UNAUTHORIZED, "Invalid Token."),
	INVALID_REQUEST(4004, HttpStatus.BAD_REQUEST, "Request invalid data."),
	DATA_MISSING(4005, HttpStatus.BAD_REQUEST, "Data missing."),
	INVALID_DATA(4007, HttpStatus.BAD_REQUEST, "Invalid Data received."),
	INVALID_USER(4008, HttpStatus.UNAUTHORIZED, "Invalid User."),
	USER_EXISTED(4009, HttpStatus.BAD_REQUEST, "User existed."),
	INVALID_PROVIDER(4010, HttpStatus.BAD_REQUEST, "Invalid Provider."),
	INVALID_TOKEN_SIGNATURE(4011, HttpStatus.UNAUTHORIZED, "Invalid JWT signature"),
	UNSUPPORTED_TOKEN(4012, HttpStatus.UNAUTHORIZED, "Unsupported JWT Token"),
	EMPTY_TOKEN_CLAIMS(4013, HttpStatus.UNAUTHORIZED, "JWT claims string is empty"),

	/* Server Error. */
	SERVER_BUSY(5001, HttpStatus.INTERNAL_SERVER_ERROR, "Server busy."),

	/**
	 * @See 서버 에러 중 아직 명시되지 않은 예외 발생 시에 사용
	 * */
	SERVER_ERROR(5100, HttpStatus.INTERNAL_SERVER_ERROR, "Unknown error.");

	private final int code;
	private final HttpStatus httpStatus;
	private final String message;
}

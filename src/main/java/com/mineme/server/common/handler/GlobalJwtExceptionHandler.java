package com.mineme.server.common.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.mineme.server.common.dto.ResponseDto;
import com.mineme.server.common.exception.CustomJwtException;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice(value = "authenticationExceptionAdvice")
@Slf4j
public class GlobalJwtExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler({CustomJwtException.class})
	@ResponseBody
	public ResponseEntity<Object> handleJwtException(CustomJwtException e) {
		log.error("handleJwtException throw JwtException : {}", e.getErrorCode().getMessage());
		return ResponseDto.toResponseEntity(e);
	}
}

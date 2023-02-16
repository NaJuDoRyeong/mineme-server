package com.mineme.server.user.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserJwtDto {
	private String jwt;
	private String code;

	public UserJwtDto(String jwt, String code) {
		this.jwt = jwt;
		this.code = code;
	}
}

package com.mineme.server.user.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserDto {

	@Getter
	@AllArgsConstructor
	public static class Jwt {
		private String jwt;
		private String code;
	}

	@Getter
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class SignRequest {
		private String accessToken;
		private String providerType;
		private String username;
	}

	@Getter
	@AllArgsConstructor
	public static class Token {
		private String accessToken;
		private String refreshToken;
	}
}

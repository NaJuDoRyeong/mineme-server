package com.mineme.server.auth.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Auth {

	@Getter
	@AllArgsConstructor
	public static class CreatedJwt {
		private boolean isCreatedNow;
		private Jwt jwt;

		public static CreatedJwt toCreatedJwtDto(boolean isCreatedNow, String jwt, String code) {
			return new Auth.CreatedJwt(isCreatedNow, new Auth.Jwt(jwt, code));
		}
	}

	@Getter
	@AllArgsConstructor
	public static class Jwt {
		private String jwt;
		private String code; // @see 커플 매칭 코드
	}

	@Getter
	@AllArgsConstructor
	public static class SignRequest {
		private String accessToken; // @Todo 제네릭으로 Kakao, Apple 구분할 것인지 파악하기
		private String providerType;
		private String username;
	}

	@Getter
	@NoArgsConstructor
	public static class Info {

		/**
		 *  @see Kakao 자원서버에서 관리하는 사용자 고유 ID, DB PK 아님.
		 **/
		private String id;

		public Info(String id) {
			this.id = id;
		}
	}

	@Getter
	public static class Token {
		private String accessToken;
		private String refreshToken;
	}
}

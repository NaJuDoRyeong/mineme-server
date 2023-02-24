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

		/**
		 * @See 사용자 커플 매칭 코드
		 */
		private String code;
	}

	@Getter
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	@AllArgsConstructor
	public static class SignRequest {
		private String accessToken; // @Todo 제네릭으로 Kakao, Apple 구분할 것인지 파악하기
		private String providerType;
		private String username;
	}

	@Getter
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class AppleSignRequest extends SignRequest {

		private String authorizationCode;

		/**
		 * @See Access Token == Identity Token
		 */
		public AppleSignRequest(String accessToken, String providerType, String username, String authorizationCode) {
			super(accessToken, providerType, username);
			this.authorizationCode = authorizationCode;
		}

		public AppleUserDto.Key getPublicKeyFromHeader() {
			return null;
		}
	}

	@Getter
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class AppleAuth {
		private String client_id;
		private String client_secret;

		/**
		 * @See authorization_code
		 */
		private String code;

		private static String grant_type = "authorization_code";

		private String refresh_token;
		private String redirect_uri;

		public AppleAuth(String clientId, String clientSecret, String code, String redirectUri) {
			this.client_id = clientId;
			this.client_secret = clientSecret;
			this.code = code;
			this.redirect_uri = redirectUri;
		}
	}

	@Getter
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class Auth {
		private String id;

		public Auth(String id) {
			this.id = id;
		}
	}

	@Getter
	@AllArgsConstructor
	public static class Token {
		private String accessToken;
		private String refreshToken;
	}
}

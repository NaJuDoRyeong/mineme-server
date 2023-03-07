package com.mineme.server.user.dto;

import com.mineme.server.security.config.Properties;
import lombok.*;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Apple {

	@Getter
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class User {
		private String id;
	}

	@Setter
	public static class SessionResponse {
		private String access_token;
		private String expires_in;
		private String id_token;
		private String refresh_token;
		private String token_type;
		private String error;

		public String getAccessToken() {
			return access_token;
		}

		public String getExpiresIn() {
			return expires_in;
		}

		public String getIdToken() {
			return id_token;
		}

		public String getRefreshToken() {
			return refresh_token;
		}

		public String getTokenType() {
			return token_type;
		}
	}

	@Getter
	@AllArgsConstructor
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class Keys {
		private List<Key> keys;
	}

	@Getter
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class Key {
		private String kty;
		private String kid;
		private String use;
		private String alg;
		private String n;
		private String e;

		public Key(String kid, String alg) {
			this.kid = kid;
			this.alg = alg;
		}
	}

	@Getter
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class SignRequest extends Auth.SignRequest {

		private String authorizationCode;

		/**
		 * @See Access Token == Identity Token
		 */
		public SignRequest(String accessToken, String providerType, String username, String authorizationCode) {
			super(accessToken, providerType, username);
			this.authorizationCode = authorizationCode;
		}

		public Apple.Key getPublicKeyFromHeader() {
			return null;
		}
	}

	@Getter
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class TokenRequest {
		private String client_id;
		private String client_secret;
		private String code; // @See authorization_code

		private static String grant_type = "authorization_code";

		private String refresh_token;
		private String redirect_uri;

		@Builder
		public TokenRequest(String clientId, String clientSecret, String code, String redirectUri) {
			this.client_id = clientId;
			this.client_secret = clientSecret;
			this.code = code;
			this.redirect_uri = redirectUri;
		}

		public static Apple.TokenRequest toAppleAuth(Properties properties, String authCode, String secret) {
			return Apple.TokenRequest.builder()
				.clientId(properties.getAppleCid())
				.clientSecret(secret)
				.code(authCode)
				.redirectUri(properties.getAppleCallback())
				.build();
		}
	}

	@Getter
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class TokenResponse {
		private String access_token;
		private String expires_in;
		private String id_token;
		private String refresh_token;
		private String token_type;
		private String error;
	}
}



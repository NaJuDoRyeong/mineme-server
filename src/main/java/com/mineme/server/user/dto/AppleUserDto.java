package com.mineme.server.user.dto;

import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AppleUserDto {

	@Getter
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class User {
		private String id;
	}

	@Getter
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class Auth {

	}

	@Getter
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	@AllArgsConstructor
	public static class KeyResponse {
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
}

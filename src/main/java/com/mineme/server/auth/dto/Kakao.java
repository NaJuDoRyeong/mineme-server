package com.mineme.server.auth.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @Todo 요청을 받는 Nested Class에 대해 카멜 케이스로 변경할 것인지 확인하기.
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Kakao {
	@Getter
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class User {
		private String id;
		private KakaoAccount kakao_account;

		public User(String id, KakaoAccount account) {
			this.id = id;
			this.kakao_account = account;
		}
	}

	@Getter
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	@AllArgsConstructor
	public static class KakaoAccount {
		private Profile profile;
	}

	@Getter
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	@AllArgsConstructor
	public static class Profile {
		private String nickname;
		private String thumbnail_image_url;
		private String profile_image_url;
		private String is_default_image;
	}
}

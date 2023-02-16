package com.mineme.server.user.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class KakaoUserDto {
	private String id;

	private KakaoAccount kakao_account;

	@Getter
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class KakaoAccount {
		private Profile profile;
	}

	@Getter
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class Profile {
		private String nickname;
		private String thumbnail_image_url;
		private String profile_image_url;
		private String is_default_image;
	}
}

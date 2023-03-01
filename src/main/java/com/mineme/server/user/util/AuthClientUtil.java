package com.mineme.server.user.util;

import java.util.List;

import org.springframework.http.HttpHeaders;

import com.mineme.server.common.utils.HttpClientUtil;
import com.mineme.server.user.dto.Apple;
import com.mineme.server.user.dto.Kakao;
import com.mineme.server.user.dto.UserDto;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import reactor.core.publisher.Mono;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AuthClientUtil {

	public static Mono<Kakao.User> getKakaoUser(UserDto.SignRequest dto) {
		return HttpClientUtil.getClient("https://kapi.kakao.com")
			.get()
			.uri("/v2/user/me")
			.header(HttpHeaders.AUTHORIZATION, "Bearer " + dto.getAccessToken())
			.header(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=utf-8")
			.retrieve()
			.bodyToMono(Kakao.User.class);
	}

	/** Apple get Public Key. **/
	public static Mono<List> getPublicKeys() {
		return HttpClientUtil.getClient("https://appleid.apple.com")
			.get()
			.uri("/auth/keys")
			.header(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded")
			.retrieve()
			.bodyToMono(List.class);
	}

	public static Mono<Apple.TokenResponse> generateAndValidateIdToken(Apple.TokenRequest appleAuth) {
		return HttpClientUtil.getClient("https://appleid.apple.com")
			.post()
			.uri("/auth/token")
			.header(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded")
			.body(Mono.just(appleAuth), Apple.TokenRequest.class)
			.retrieve()
			.bodyToMono(Apple.TokenResponse.class);
	}
}

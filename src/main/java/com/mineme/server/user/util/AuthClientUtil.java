package com.mineme.server.user.util;

import java.util.List;

import org.springframework.http.HttpHeaders;

import com.mineme.server.common.utils.HttpClientUtil;
import com.mineme.server.user.dto.Apple;
import com.mineme.server.user.dto.Kakao;
import com.mineme.server.user.dto.Auth;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import reactor.core.publisher.Mono;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AuthClientUtil {

	private static String APPLE_ID_BASE_API = "https://appleid.apple.com";
	private static String CONTENT_TYPE_FORM_URL_ENCODED = "application/x-www-form-urlencoded";

	public static Mono<Kakao.User> getKakaoUser(Auth.SignRequest dto) {
		return HttpClientUtil.getClient("https://kapi.kakao.com")
			.get()
			.uri("/v2/user/me")
			.header(HttpHeaders.AUTHORIZATION, "Bearer " + dto.getAccessToken())
			.header(HttpHeaders.CONTENT_TYPE, CONTENT_TYPE_FORM_URL_ENCODED + ";charset=utf-8")
			.retrieve()
			.bodyToMono(Kakao.User.class);
	}

	/** Apple get Public Key. **/
	public static Mono<List> getPublicKeys() {
		return HttpClientUtil.getClient(APPLE_ID_BASE_API)
			.get()
			.uri("/auth/keys")
			.header(HttpHeaders.CONTENT_TYPE, CONTENT_TYPE_FORM_URL_ENCODED)
			.retrieve()
			.bodyToMono(List.class);
	}

	public static Mono<Apple.TokenResponse> generateAndValidateIdToken(Apple.TokenRequest appleAuth) {
		return HttpClientUtil.getClient(APPLE_ID_BASE_API)
			.post()
			.uri("/auth/token")
			.header(HttpHeaders.CONTENT_TYPE, CONTENT_TYPE_FORM_URL_ENCODED)
			.body(Mono.just(appleAuth), Apple.TokenRequest.class)
			.retrieve()
			.bodyToMono(Apple.TokenResponse.class);
	}
}

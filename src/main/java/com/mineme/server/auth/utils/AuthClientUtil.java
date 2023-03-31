package com.mineme.server.auth.utils;

import com.mineme.server.auth.dto.Apple;
import com.mineme.server.auth.dto.Auth;
import com.mineme.server.auth.dto.Kakao;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpHeaders;
import reactor.core.publisher.Mono;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AuthClientUtil {

	private static String APPLE_ID_BASE_API = "https://appleid.apple.com";
	private static String CONTENT_TYPE_FORM_URL_ENCODED = "application/x-www-form-urlencoded";
	private static String CONTENT_TYPE_POST_FIX_CHARSET = ";charset=utf-8";

	public static Mono<Kakao.User> getKakaoUser(Auth.SignRequest dto) {
		return HttpClientUtil.getClient("https://kapi.kakao.com")
			.get()
			.uri("/v2/user/me")
			.header(HttpHeaders.AUTHORIZATION, "Bearer " + dto.getAccessToken())
			.header(HttpHeaders.CONTENT_TYPE, CONTENT_TYPE_FORM_URL_ENCODED + CONTENT_TYPE_POST_FIX_CHARSET)
			.retrieve()
			.bodyToMono(Kakao.User.class);
	}

	/** Apple get Public Key. **/
	public static Mono<Apple.Keys> getPublicKeys() {
		return HttpClientUtil.getClient(APPLE_ID_BASE_API)
			.get()
			.uri("/auth/keys")
			.header(HttpHeaders.CONTENT_TYPE, CONTENT_TYPE_FORM_URL_ENCODED)
			.retrieve()
			.bodyToMono(Apple.Keys.class);
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

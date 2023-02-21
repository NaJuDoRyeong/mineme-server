package com.mineme.server.user.util;

import com.mineme.server.user.dto.KakaoUserDto;
import com.mineme.server.user.dto.UserDto;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HttpClientUtil {

	public static WebClient getClient(String base) {
		return WebClient.builder()
			.baseUrl(base)
			.defaultHeader(HttpHeaders.CONTENT_TYPE, HttpHeaders.AUTHORIZATION)
			.build();
	}

	public static Mono<KakaoUserDto.User> getMonoUser(UserDto.SignRequest dto) {
		return HttpClientUtil.getClient("https://kapi.kakao.com")
			.get()
			.uri("/v2/user/me")
			.header(HttpHeaders.AUTHORIZATION, "Bearer " + dto.getAccessToken())
			.header(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=utf-8")
			.retrieve()
			.bodyToMono(KakaoUserDto.User.class);
	}
}

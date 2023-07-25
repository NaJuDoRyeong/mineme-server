package com.mineme.server.auth.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.WebClient;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HttpClientUtil {

	public static WebClient getClient(String base) {
		return WebClient.builder()
			.baseUrl(base)
			.defaultHeader(HttpHeaders.CONTENT_TYPE, HttpHeaders.AUTHORIZATION)
			.build();
	}
}

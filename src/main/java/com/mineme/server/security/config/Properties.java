package com.mineme.server.security.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
@Slf4j
public class Properties {

	private final Environment environment;

	@Getter
	private String appleTid;

	@Getter
	private String appleKid;

	@Getter
	private String appleCid;

	@Getter
	private String appleKeyPath;

	@Getter
	private String appleCallback;

	@Getter
	private String secret;

	/**
	 * @Todo 추후 배포 단계에서 로깅 제거
	 */
	@PostConstruct
	public void init() {
		this.secret = environment.getProperty("JWT_SECRET");
		log.info("JWT_SECRET is " + this.secret);

		this.appleKid = environment.getProperty("APPLE_KEY_ID");
		log.info("APPLE_KEY_ID is " + this.appleKid);

		this.appleTid = environment.getProperty("APPLE_TEAM_ID");
		log.info("APPLE_TEAM_ID is " + this.appleTid);

		this.appleCid = environment.getProperty("APPLE_CLIENT_ID");
		log.info("APPLE_CLIENT_ID is " + this.appleCid);

		this.appleCallback = environment.getProperty("APPLE_CALLBACK");
		log.info("APPLE_CALLBACK is " + this.appleCallback);
	}
}

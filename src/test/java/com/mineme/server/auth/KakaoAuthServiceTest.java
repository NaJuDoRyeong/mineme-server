package com.mineme.server.auth;

import static org.mockito.Mockito.*;

import java.io.IOException;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mineme.server.entity.User;
import com.mineme.server.security.config.Properties;
import com.mineme.server.security.provider.JwtTokenProvider;
import com.mineme.server.user.dto.Auth;
import com.mineme.server.user.dto.Kakao;
import com.mineme.server.user.service.KakaoAuthService;
import com.mineme.server.user.util.AuthClientUtil;

import reactor.core.publisher.Mono;

@RunWith(SpringRunner.class)
@RestClientTest(KakaoAuthService.class)
public class KakaoAuthServiceTest {

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@Mock
	private Properties properties;

	/*
	 * @todo 추후 `Value`를 통한 필드 주입이 적절한지 확인하고 수정이 필요한 경우 수정하기
	 * */
	@Value("${test.auth.kakao.access_token}")
	private String ACCESS_TOKEN;

	@Value("${test.auth.kakao.jwt_secret}")
	private String JWT_SECRET;

	@Test
	public void kakaoJwtTest() throws IOException, NullPointerException {
		//given
		Mono<Kakao.User> monoUser = Mono.just(
			new ObjectMapper().readValue(new ClassPathResource("/auth/kakaouser.json").getFile(), Kakao.User.class));
		Auth.SignRequest dto = new Auth.SignRequest(ACCESS_TOKEN, "KAKAO", "user_nickname");

		//when
		doReturn(monoUser).when(AuthClientUtil.getKakaoUser(dto));
		doReturn(JWT_SECRET).when(properties).getSecret();
		Kakao.User user = monoUser.block();
		User signedUser = User.toPendingUserEntity(user.getId(), dto);

		//then
		String userAccessToken = jwtTokenProvider.create(user.getId(), signedUser.getUserState(),
			properties.getSecret());
		Assertions.assertTrue(jwtTokenProvider.validate(userAccessToken, properties.getSecret()));
	}
}
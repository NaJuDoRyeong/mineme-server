package com.mineme.server.auth;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Map;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mineme.server.entity.enums.Provider;
import com.mineme.server.entity.enums.UserState;
import com.mineme.server.security.provider.JwtTokenProvider;
import com.mineme.server.security.util.JwtUtil;
import com.mineme.server.auth.dto.Apple;
import com.mineme.server.auth.service.AppleAuthService;
import com.mineme.server.auth.utils.AuthUtil;

@RunWith(SpringRunner.class)
@RestClientTest(AppleAuthService.class)
public class AppleAuthServiceTest {

	@Autowired
	private AppleAuthService appleAuthService;
	private JwtTokenProvider jwtTokenProvider;

	/*
	 * @todo 추후 `Value`를 통한 필드 주입이 적절한지 확인하고 수정이 필요한 경우 수정하기
	 * */
	@Value("${test.auth.apple.access_token}")
	private String ACCESS_TOKEN;

	private PublicKey getPublicKey(String keyPath) throws
		NoSuchAlgorithmException,
		InvalidKeySpecException,
		IOException {
		byte[] keyBytes = Files.readAllBytes(Paths.get(keyPath));
		X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
		KeyFactory kf = KeyFactory.getInstance("RSA");
		return kf.generatePublic(spec);
	}

	@Test
	public void appleHeaderValidateTest() throws IOException, NoSuchAlgorithmException {
		//given
		Apple.SignRequest dto = new Apple.SignRequest(ACCESS_TOKEN, Provider.APPLE.toString(), "authCodeSample");

		//when
		Apple.Keys keys = new ObjectMapper().readValue(new ClassPathResource("/auth/apple_publickey.json").getFile(),
			Apple.Keys.class);
		Map<String, String> header = JwtUtil.getHeader(dto.getAccessToken());

		//then
		keys.getKeys().forEach(key -> Assertions.assertNotEquals(((Apple.Key)key).getKid(), header.get("kid")));
	}

	@Test
	public void appleTokenValidateByPublicKeyTest() throws
		IOException,
		NoSuchAlgorithmException,
		InvalidKeySpecException {
		//given
		PublicKey publicKey = getPublicKey("/auth/PublicKeyForTest2.notrealkey");
		PrivateKey privateKey = AuthUtil.getPrivateKey("/auth/PrivateKeyForTest2.notrealkey");

		//when
		String JWS_ACCESS_TOKEN = jwtTokenProvider.create("appleuser", UserState.PENDING, privateKey);

		//then
		Assertions.assertTrue(jwtTokenProvider.validate(JWS_ACCESS_TOKEN, publicKey));
	}
}

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
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mineme.server.entity.enums.Provider;
import com.mineme.server.entity.enums.UserState;
import com.mineme.server.security.provider.JwtTokenProvider;
import com.mineme.server.security.util.JwtUtil;
import com.mineme.server.user.dto.Apple;
import com.mineme.server.user.service.AppleAuthService;
import com.mineme.server.user.util.AuthUtil;

@RunWith(SpringRunner.class)
@RestClientTest(AppleAuthService.class)
public class AppleAuthServiceTest {

	@Autowired
	private AppleAuthService appleAuthService;
	private JwtTokenProvider jwtTokenProvider;

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
	public void appleHeaderValidateTest() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
		//given
		ACCESS_TOKEN = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6ImZoNkJzOEMifQ.eyJzdWIiOiIxMjM0NTY3ODkwIiwidXNlcm5hbWUiOiIxMjM0NTY3ODkwIiwic3RhdGUiOiJQRU5ESU5HIiwiaWF0IjoxNTE2MjM5MDIyfQ.X3KI1_9wamehFJmFJj6iBFhmUVQuqzjrS6Y6whFvqXyFUXL5eWjtIiUkCjOY0spdCM4foF-w5x8Z0SqOZt7eaAY2qPzgEE_a7Vfsc9pOC-QHDfDY0olMvAAn4NV2FGzz9gdIl4GriTgZXdL8rcH86K9ey43ZR0CElX3D8x1XLyvxgvsajq8I1JiKa1MIRYO2NvMYezyzjgJ7_UWiylkzUi2FxhUsVJNy4wAHywhZnwv1zPl5ugzRM2ToZuSM_8f6gtQJm1OQG442e0xdpZCZZs9UI8MyB8L42naMULjgaAQI--GKfY_godCCx_nhA5he5w2CRSK1DyIYwGv6RBg1VA";
		Apple.SignRequest dto = new Apple.SignRequest(ACCESS_TOKEN, Provider.APPLE.toString(), "appleuser",
			"authCodeSample");

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
		PublicKey publicKey = getPublicKey("/auth/PublicKeyForTest2.p8");
		PrivateKey privateKey = AuthUtil.getPrivateKey("/auth/PrivateKeyForTest2.p8");

		//when
		ACCESS_TOKEN = jwtTokenProvider.create("appleuser", UserState.PENDING, privateKey);

		//then
		Assertions.assertTrue(jwtTokenProvider.validate(ACCESS_TOKEN, publicKey));
	}
}

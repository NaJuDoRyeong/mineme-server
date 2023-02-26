package com.mineme.server.user.util;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;

import com.mineme.server.common.utils.HttpClientUtil;
import com.mineme.server.security.config.Properties;
import com.mineme.server.security.util.JwtUtil;
import com.mineme.server.user.dto.AppleUserDto;
import com.mineme.server.user.dto.KakaoUserDto;
import com.mineme.server.user.dto.UserDto;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AuthUtil {

	public static Mono<KakaoUserDto.User> getKakaoUser(UserDto.SignRequest dto) {
		return HttpClientUtil.getClient("https://kapi.kakao.com")
			.get()
			.uri("/v2/user/me")
			.header(HttpHeaders.AUTHORIZATION, "Bearer " + dto.getAccessToken())
			.header(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=utf-8")
			.retrieve()
			.bodyToMono(KakaoUserDto.User.class);
	}

	/** Apple
	 * step 1. get Public Key for validation.
	 **/
	public static List<PublicKey> getApplePublicKeys(Properties properties, UserDto.AppleSignRequest dto) throws
		NoSuchAlgorithmException,
		InvalidKeySpecException {

		/* 1. Client 비밀 값 생성 */
		String appleClientSecret = JwtUtil.getAuthJws(properties.getAppleTid(), properties.getAppleCid(),
			properties.getAppleKid(), properties.getAppleKeyPath());

		UserDto.AppleAuth appleAuth = UserDto.AppleAuth.toAppleAuth(properties, dto.getAuthorizationCode(),
			appleClientSecret);

		/* 공개키 생성을 위한 인자 요청 */
		List<AppleUserDto.Key> keys = HttpClientUtil.getClient("https://appleid.apple.com")
			.post()
			.uri("/auth/token")
			.header(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded")
			.body(Mono.just(appleAuth), UserDto.AppleAuth.class)
			.retrieve()
			.bodyToMono(List.class)
			.block();

		/* 공개 키 생성 */
		Map<String, String> header = JwtUtil.getHeader(dto.getAccessToken());
		List<PublicKey> pks = new ArrayList<>();
		for (AppleUserDto.Key key : keys) {
			if (header.get("kid").equals(key.getKid()) && header.get("alg").equals(key.getAlg())) {

				byte[] nBytes = java.util.Base64.getUrlDecoder().decode(key.getN());
				byte[] eBytes = Base64.getUrlDecoder().decode(key.getE());

				BigInteger n = new BigInteger(1, nBytes);
				BigInteger e = new BigInteger(1, eBytes);

				RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(n, e);
				KeyFactory keyFactory = KeyFactory.getInstance(key.getKty());
				PublicKey applePublicKey = keyFactory.generatePublic(publicKeySpec);

				pks.add(applePublicKey);
				break;
			}
		}

		return pks;
	}

	/** Apple
	 * step 2. get Auth by Public Key.
	 **/
	public static Mono<AppleUserDto.Auth> getAppleAuth(UserDto.AppleSignRequest dto) {
		return HttpClientUtil.getClient("https://appleid.apple.com")
			.get()
			.uri("/auth/keys")
			.header(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded")
			.retrieve()
			.bodyToMono(AppleUserDto.Auth.class);
	}

	/** Apple
	 * step 3. get ID from verified Token.
	 */
	public static AppleUserDto.User getAppleUser(AppleUserDto.Auth auth) {

		return null;
	}
}

package com.mineme.server.user.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Calendar;
import java.util.Date;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.HttpHeaders;

import com.fasterxml.jackson.core.io.JsonEOFException;
import com.mineme.server.common.enums.ErrorCode;
import com.mineme.server.common.exception.CustomException;
import com.mineme.server.common.utils.HttpClientUtil;
import com.mineme.server.security.config.Properties;
import com.mineme.server.user.dto.AppleUserDto;
import com.mineme.server.user.dto.KakaoUserDto;
import com.mineme.server.user.dto.UserDto;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AuthUtil {
	private static long EXPIREDTIME = 60 * 60 * 1000;

	public static String getAuthJws(String teamId, String clientId, String keyId, String keyPath) {
		try {
			final String privateKey = readPrivateKey(keyPath);
			PrivateKey ecPrivateKey = getPrivateKey(privateKey);

			return Jwts.builder()
				.setHeaderParam("kid", keyId)
				.setIssuer(teamId)
				.setSubject(clientId)
				.setIssuedAt(new Date(Calendar.getInstance().getTimeInMillis()))
				.setExpiration(new Date(Calendar.getInstance().getTimeInMillis() + EXPIREDTIME))
				.setAudience("appstoreconnect-v1")
				.claim("bid", "com.mineme.ios.mineme")
				.signWith(SignatureAlgorithm.ES256, ecPrivateKey)
				.compact();

			/**
			 * @Todo 테스트 후 예외 처리 상세화 예정
			 */
		} catch (NoSuchAlgorithmException e) {
			log.error("ES256 Signature Not Found. {}", e.getMessage());
			throw new CustomException(ErrorCode.INVALID_TOKEN);
		} catch (JsonEOFException e) {
			log.error("JsonEOFException. {}", e.getMessage());
			throw new CustomException(ErrorCode.SERVER_ERROR);
		} catch (IOException e) {
			log.error("IOException. {}", e.getMessage());
			throw new CustomException(ErrorCode.SERVER_ERROR);
		} catch (InvalidKeySpecException e) {
			log.error("InvalidKeySpecException. {}", e.getMessage());
			throw new CustomException(ErrorCode.INVALID_TOKEN);
		}
	}

	private static PrivateKey getPrivateKey(String key) throws NoSuchAlgorithmException, InvalidKeySpecException {
		byte[] encoded = Base64.decodeBase64(key);
		return KeyFactory.getInstance("EC").generatePrivate(new PKCS8EncodedKeySpec(encoded));
	}

	private static String readPrivateKey(String filePath) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader(filePath));

		String line;
		StringBuilder sb = new StringBuilder();
		while ((line = br.readLine()) != null) {
			if (line.contains("PRIVATE KEY")) {
				continue;
			}
			sb.append(line);
		}

		return sb.toString();
	}

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
	public static Mono<AppleUserDto.KeyResponse> getApplePublicKey(Properties properties,
		UserDto.AppleSignRequest dto) {

		String appleClientSecret = AuthUtil.getAuthJws(properties.getAppleTid(), properties.getAppleCid(),
			properties.getAppleKid(), properties.getAppleKeyPath());
		UserDto.AppleAuth appleAuth = new UserDto.AppleAuth(properties.getAppleCid(), appleClientSecret,
			dto.getAuthorizationCode(), properties.getAppleCallback());

		return HttpClientUtil.getClient("https://appleid.apple.com")
			.post()
			.uri("/auth/token")
			.header(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded")
			.body(Mono.just(appleAuth), UserDto.AppleAuth.class)
			.retrieve()
			.bodyToMono(AppleUserDto.KeyResponse.class);
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
}

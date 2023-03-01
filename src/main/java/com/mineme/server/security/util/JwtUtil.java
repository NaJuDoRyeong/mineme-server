package com.mineme.server.security.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.io.JsonEOFException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mineme.server.common.enums.ErrorCode;
import com.mineme.server.common.exception.CustomException;
import com.mineme.server.user.util.AuthUtil;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class JwtUtil {

	private static long EXPIRED_TIME = 60 * 60 * 1000;

	public static Map<String, String> getHeader(String token) {
		try {
			String[] chunks = token.split("\\.");
			Base64.Decoder decoder = Base64.getUrlDecoder();
			String header = new String(decoder.decode(chunks[0]));

			ObjectMapper objMapper = new ObjectMapper();

			return objMapper.readValue(header, new TypeReference<Map<String, String>>() {
			});
		} catch (JsonProcessingException e) {
			throw new CustomException(ErrorCode.INVALID_TOKEN);
		}
	}

	public static String getAppleClientSecret(String teamId, String clientId, String keyId, String keyPath) {
		try {
			return Jwts.builder()
				.setHeaderParam("kid", keyId)
				.setIssuer(teamId)
				.setSubject(clientId)
				.setIssuedAt(new Date(Calendar.getInstance().getTimeInMillis()))
				.setExpiration(new Date(Calendar.getInstance().getTimeInMillis() + EXPIRED_TIME))
				.setAudience("https://appleid.apple.com")
				.claim("bid", "com.mineme.ios.mineme")
				.signWith(SignatureAlgorithm.ES256, AuthUtil.getPrivateKey())
				.compact();

			/**
			 * @Todo 테스트 후 예외 처리 상세화 예정
			 */
		} catch (JsonEOFException e) {
			log.error("JsonEOFException. {}", e.getMessage());
			throw new CustomException(ErrorCode.SERVER_ERROR);
		} catch (IOException e) {
			log.error("IOException. {}", e.getMessage());
			throw new CustomException(ErrorCode.SERVER_ERROR);
		}
	}

	@Deprecated
	private static PrivateKey getPrivateKey(String key) throws NoSuchAlgorithmException, InvalidKeySpecException {
		byte[] encoded = org.apache.tomcat.util.codec.binary.Base64.decodeBase64(key);
		return KeyFactory.getInstance("EC").generatePrivate(new PKCS8EncodedKeySpec(encoded));
	}

	@Deprecated
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
}

package com.mineme.server.user.util;

import com.mineme.server.common.enums.ErrorCode;
import com.mineme.server.common.exception.CustomException;
import com.mineme.server.security.util.JwtUtil;
import com.mineme.server.user.dto.Apple;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AuthUtil {

	/**
	 * Apple
	 * step 1. get Public Key for validation.
	 **/
	public static PublicKey getApplePublicKeys(Apple.SignRequest dto) throws
		NoSuchAlgorithmException,
		InvalidKeySpecException {

		/* 공개키 요청 */
		List<Apple.Key> keys = AuthClientUtil.getPublicKeys().block().getKeys();

		/* 공개 키 확인 */
		Map<String, String> header = JwtUtil.getHeader(dto.getAccessToken());

		Apple.Key validKey = getValidKey(keys, header.get("kid"), header.get("alg")).orElseThrow(
			() -> new CustomException(ErrorCode.INVALID_TOKEN));

		return getDecodedKey(validKey);
	}

	private static Optional<Apple.Key> getValidKey(List<Apple.Key> keys, String kid, String alg) {
		return keys.stream().filter(key -> key.getKid().equals(kid) && key.getAlg().equals(alg)).findFirst();
	}

	public static PublicKey getDecodedKey(Apple.Key validKey) throws InvalidKeySpecException, NoSuchAlgorithmException {

		byte[] nBytes = Base64.getUrlDecoder().decode(validKey.getN());
		byte[] eBytes = Base64.getUrlDecoder().decode(validKey.getE());

		BigInteger n = new BigInteger(1, nBytes);
		BigInteger e = new BigInteger(1, eBytes);

		RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(n, e);
		KeyFactory keyFactory = KeyFactory.getInstance(validKey.getKty());
		return keyFactory.generatePublic(publicKeySpec);
	}

	public static PrivateKey getPrivateKey(String keyPath) throws IOException {
		ClassPathResource resource = new ClassPathResource(keyPath);
		String privateKey = new String(Files.readAllBytes(Paths.get(resource.getURI())));
		Reader pemReader = new StringReader(privateKey);
		PEMParser pemParser = new PEMParser(pemReader);
		JcaPEMKeyConverter converter = new JcaPEMKeyConverter();
		PrivateKeyInfo object = (PrivateKeyInfo)pemParser.readObject();
		return converter.getPrivateKey(object);
	}
}
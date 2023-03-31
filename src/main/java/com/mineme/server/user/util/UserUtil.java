package com.mineme.server.user.util;

import java.security.NoSuchAlgorithmException;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserUtil {

	public static String encodeCodeToBase62(Long rawCode) {
		Base62 base62 = Base62.createInstance();
		final byte[] encoded = base62.encode(rawCode.toString().getBytes());

		return new String(encoded);
	}

	public static String decodeCodeFromBase62(String encodedCode) {
		Base62 base62 = Base62.createInstance();
		final byte[] decode = base62.decode(encodedCode.getBytes());

		return new String(decode);
	}

	public static String createUserCode(Long rawCode) {
		String encodeStr = encodeCodeToBase62(rawCode);
		log.info("base62 encode result:" + encodeStr);
		return encodeStr;
	}
}

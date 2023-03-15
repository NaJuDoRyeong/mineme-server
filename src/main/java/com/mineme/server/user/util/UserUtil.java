package com.mineme.server.user.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserUtil {

	public static String createUserCode(Long rawCode) {
		Base62 base62 = Base62.createInstance();
		final byte[] encoded = base62.encode(rawCode.toString().getBytes());

		return new String(encoded);
	}
}

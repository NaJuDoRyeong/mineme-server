package com.mineme.server.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Utils {

	public static boolean stringToBoolean (String str) {
		return str.equals("y");
	}
}

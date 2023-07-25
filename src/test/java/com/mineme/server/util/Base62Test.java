package com.mineme.server.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.mineme.server.user.util.Base62;

@DisplayName("Base62 Test")
public class Base62Test {

	private final Base62 standardEncoder = Base62.createInstance();

	private final Base62[] encoders = {Base62.createInstanceWithGmpCharacterSet(),
		Base62.createInstanceWithInvertedCharacterSet()};

	@Test
	@DisplayName("바이트 배열을 유지해야 함.")
	public void preservesIdentityTest() {
		for (byte[] message : Base62TestEnv.getRawInputs()) {
			for (Base62 encoder : encoders) {
				final byte[] encoded = encoder.encode(message);
				final byte[] decoded = encoder.decode(encoded);

				assertArrayEquals(message, decoded);
			}
		}
	}

	@Test
	@DisplayName("인코딩이 영문과 숫자만을 포함해야 함.")
	public void alphaNumericOutputTest() {
		for (byte[] message : Base62TestEnv.getRawInputs()) {
			for (Base62 encoder : encoders) {
				final byte[] encoded = encoder.encode(message);
				final String encodedStr = new String(encoded);

				assertTrue(isAlphaNumeric(encodedStr));
			}
		}
	}

	@Test
	@DisplayName("비어있는 입력을 처리해야 함.")
	public void emptyInputsTest() {
		final byte[] empty = new byte[0];

		for (Base62 encoder : encoders) {
			final byte[] encoded = encoder.encode(empty);
			assertArrayEquals(empty, encoded);

			final byte[] decoded = encoder.decode(empty);
			assertArrayEquals(empty, decoded);
		}
	}

	@Test
	@DisplayName("비정상적인 인코딩에 예외를 발생시켜야 함.")
	public void wrongEncodingTest() {
		for (final byte[] input : Base62TestEnv.getWrongEncoding()) {
			assertThrows(IllegalArgumentException.class, new Executable() {
				@Override
				public void execute() throws Throwable {
					standardEncoder.decode(input);
				}
			});
		}
	}

	@Test
	@DisplayName("Null 입력을 디코딩할 시 예외 처리 해야 함.")
	public void decodeNullTest() {
		assertThrows(IllegalArgumentException.class, new Executable() {
			@Override
			public void execute() throws Throwable {
				standardEncoder.decode(null);
			}
		});
	}

	@Test
	@DisplayName("인코딩이 정확해야 함.")
	public void checkEncodingTest() {
		assertTrue(standardEncoder.isBase62Encoding("0123456789".getBytes()));
		assertTrue(standardEncoder.isBase62Encoding("abcdefghijklmnopqrstuvwxzy".getBytes()));
		assertTrue(standardEncoder.isBase62Encoding("ABCDEFGHIJKLMNOPQRSTUVWXZY".getBytes()));

		assertFalse(standardEncoder.isBase62Encoding("!".getBytes()));
		assertFalse(standardEncoder.isBase62Encoding("@".getBytes()));
		assertFalse(standardEncoder.isBase62Encoding("<>".getBytes()));
		assertFalse(standardEncoder.isBase62Encoding("abcd%".getBytes()));
		assertFalse(standardEncoder.isBase62Encoding("😱".getBytes()));
	}

	private String encode(final String input) {
		return new String(standardEncoder.encode(input.getBytes()));
	}

	private boolean isAlphaNumeric(final String str) {
		return str.matches("^[a-zA-Z0-9]+$");
	}
}
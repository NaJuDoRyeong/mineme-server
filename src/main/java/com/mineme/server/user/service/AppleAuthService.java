package com.mineme.server.user.service;

import com.mineme.server.common.enums.ErrorCode;
import com.mineme.server.common.exception.CustomException;
import com.mineme.server.entity.User;
import com.mineme.server.security.config.Properties;
import com.mineme.server.security.provider.JwtTokenProvider;
import com.mineme.server.security.util.JwtUtil;
import com.mineme.server.user.dto.Apple;
import com.mineme.server.user.dto.Auth;
import com.mineme.server.user.repository.UserRepository;
import com.mineme.server.user.util.AuthClientUtil;
import com.mineme.server.user.util.AuthUtil;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;

@Service
@RequiredArgsConstructor
public class AppleAuthService implements AuthService<Apple.SignRequest> {
	private final UserRepository userRepository;
	private final JwtTokenProvider jwtTokenProvider;
	private final Properties properties;

	@Transactional
	@Override
	public Auth.Jwt getUserDetails(Apple.SignRequest dto) {
		try {
			/* 공개 키 가져오기 */
			PublicKey key = AuthUtil.getApplePublicKeys(dto);

			/* 공개 키를 통한 Identity Token 검증 */
			if (!jwtTokenProvider.validate(dto.getAccessToken(), key))
				throw new CustomException(ErrorCode.INVALID_TOKEN);

			String username = jwtTokenProvider.getClaims(dto.getAccessToken(), key).getSubject();
			User signedUser = userRepository.findByUsername(username).orElse(null);

			if (signedUser == null)
				signedUser = userRepository.save(User.toPendingUserEntity(username, dto));

			String accessToken = jwtTokenProvider.create(signedUser.getUsername(), properties.getSecret());

			return new Auth.Jwt(accessToken, signedUser.getUserCode());
		} catch (NullPointerException e) { // @Todo - 추후 orElse() 로직 변경 시 함께 조정 해야 함.
			throw new CustomException(ErrorCode.INVALID_USER);
		} catch (WebClientResponseException e) {
			throw new CustomException(ErrorCode.INVALID_TOKEN);
		} catch (InvalidKeySpecException e) {
			throw new CustomException(ErrorCode.INVALID_TOKEN);
		} catch (NoSuchAlgorithmException e) {
			throw new CustomException(ErrorCode.INVALID_TOKEN);
		}
	}

	public Apple.TokenResponse getSession(Apple.SignRequest dto) {

		String clientSecret = JwtUtil.getAppleClientSecret(properties.getAppleTid(), properties.getAppleCid(),
			properties.getAppleKid(), properties.getAppleKeyPath());

		Apple.TokenRequest authDto = Apple.TokenRequest.toAppleAuth(properties, dto.getAuthorizationCode(),
			clientSecret);

		/* @Todo Refresh Token 을 이용한 로직 추가해야 함. */

		return AuthClientUtil.generateAndValidateIdToken(authDto).block();
	}

	@Override
	public User getCurrentUser() {
		return jwtTokenProvider.getUsername()
			.flatMap(username -> userRepository.findByUsername(username))
			.orElseThrow(() -> new CustomException(ErrorCode.INVALID_USER));
	}
}

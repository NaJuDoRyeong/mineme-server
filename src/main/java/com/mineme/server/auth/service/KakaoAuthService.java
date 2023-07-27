package com.mineme.server.auth.service;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.mineme.server.auth.dto.Auth;
import com.mineme.server.auth.dto.Kakao;
import com.mineme.server.auth.utils.AuthClientUtil;
import com.mineme.server.common.enums.ErrorCode;
import com.mineme.server.common.exception.CustomException;
import com.mineme.server.entity.User;
import com.mineme.server.security.config.Properties;
import com.mineme.server.security.provider.JwtTokenProvider;
import com.mineme.server.user.dto.UserBuilder;
import com.mineme.server.user.repository.UserRepository;
import com.mineme.server.user.service.UserService;
import com.mineme.server.user.service.UserServiceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoAuthService implements AuthService<Auth.SignRequest> {

	private final UserService userService;
	private final UserRepository userRepository;
	private final JwtTokenProvider jwtTokenProvider;
	private final Properties properties;

	@Transactional
	@Override
	public Auth.CreatedJwt getUserDetails(Auth.SignRequest dto) {
		try {
			Kakao.User kakaoUser = AuthClientUtil.getKakaoUser(dto).block();

			if (kakaoUser == null)
				throw new CustomException(ErrorCode.INVALID_USER);

			String username = kakaoUser.getId();
			Optional<User> userOptional = userRepository.findByUsername(username);

			if (userOptional.isPresent()) {
				User signedUser = userOptional.get();

				String accessToken = jwtTokenProvider.create(signedUser.getUsername(), signedUser.getUserState(),
					properties.getSecret());

				return Auth.CreatedJwt.toCreatedJwtDto(false, accessToken,
					userService.getUserMatchingCode(signedUser));
			} else {
				User pendingUser = UserBuilder.toPendingUserEntity(username, dto);
				User signedUser = userRepository.save(pendingUser);

				String accessToken = jwtTokenProvider.create(signedUser.getUsername(), signedUser.getUserState(),
					properties.getSecret());

				return Auth.CreatedJwt.toCreatedJwtDto(true, accessToken,
					userService.getUserMatchingCode(signedUser));
			}
		} catch (NullPointerException e) {
			throw new CustomException(ErrorCode.INVALID_USER);
		} catch (WebClientResponseException e) {
			throw new CustomException(ErrorCode.INVALID_TOKEN);
		} catch (Exception e) {
			throw new CustomException(ErrorCode.SERVER_ERROR);
		}
	}
}

package com.mineme.server.user.service;

import com.mineme.server.common.enums.ErrorCode;
import com.mineme.server.common.exception.CustomException;
import com.mineme.server.entity.User;
import com.mineme.server.security.config.Properties;
import com.mineme.server.security.provider.JwtTokenProvider;
import com.mineme.server.user.dto.KakaoUserDto;
import com.mineme.server.user.dto.UserDto;
import com.mineme.server.user.repository.UserRepository;
import com.mineme.server.user.util.HttpClientUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Service
@Slf4j
@RequiredArgsConstructor
public class KakaoAuthService {

	private final UserRepository userRepository;
	private final JwtTokenProvider jwtTokenProvider;
	private final Properties properties;

	@Transactional
	public UserDto.Jwt getKakaoUserDetails(UserDto.SignRequest dto) {
		try {
			KakaoUserDto.User user = HttpClientUtil.getMonoUser(dto).block();
			User signedUser = userRepository.findByUsername(user.getId()).orElse(null);

			if (signedUser == null)
				signedUser = userRepository.save(User.toPendingUserEntity(user.getId(), dto));

			String accessToken = jwtTokenProvider.create(signedUser.getUsername(), properties.getSecret());

			return new UserDto.Jwt(accessToken, signedUser.getUserCode());
		} catch (NullPointerException e) { // @Todo - 추후 orElse() 로직 변경 시 함께 조정해야 함.
			throw new CustomException(ErrorCode.INVALID_USER);
		} catch (WebClientResponseException e) {
			throw new CustomException(ErrorCode.INVALID_TOKEN);
		}
	}
}

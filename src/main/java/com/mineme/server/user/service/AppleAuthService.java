package com.mineme.server.user.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.mineme.server.common.enums.ErrorCode;
import com.mineme.server.common.exception.CustomException;
import com.mineme.server.entity.User;
import com.mineme.server.security.config.Properties;
import com.mineme.server.security.provider.JwtTokenProvider;
import com.mineme.server.user.dto.AppleUserDto;
import com.mineme.server.user.dto.UserDto;
import com.mineme.server.user.repository.UserRepository;
import com.mineme.server.user.util.AuthUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AppleAuthService {
	private final UserRepository userRepository;
	private final JwtTokenProvider jwtTokenProvider;
	private final Properties properties;

	@Transactional
	public UserDto.Jwt getUserDetails(UserDto.AppleSignRequest dto) {
		try {

			/* @Todo Step 2 지정 후 함께 적용 */
			AppleUserDto.Auth user = AuthUtil.getAppleAuth(dto).block();
			User signedUser = userRepository.findByUsername(null).orElse(null);

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

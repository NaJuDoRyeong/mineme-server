package com.mineme.server.auth.service;

import org.springframework.stereotype.Component;

import com.mineme.server.common.enums.ErrorCode;
import com.mineme.server.common.exception.CustomException;
import com.mineme.server.entity.User;
import com.mineme.server.entity.enums.UserState;
import com.mineme.server.security.config.Properties;
import com.mineme.server.security.provider.JwtTokenProvider;
import com.mineme.server.auth.dto.Auth;
import com.mineme.server.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public abstract class AuthService<T> {

	public final JwtTokenProvider jwtTokenProvider;
	public final UserRepository userRepository;
	public final Properties properties;

	public abstract Auth.CreatedJwt getUserDetails(T dto);

	/**
	 * @todo 빌드 에러로 임시 수정
	 */
	public User getCurrentUser() {
		String username = jwtTokenProvider.getUsername();
		return userRepository.findByUsername(username).orElseThrow(() -> new CustomException(ErrorCode.INVALID_USER));
	}

	public void isValidCurrentUserState(UserState userState) {
		if (getCurrentUser().getUserState() != userState)
			throw new CustomException(ErrorCode.INVALID_USER);
	}

	/**
	 * jwt 로부터 현재 커플 매칭 완료 상태, 정상적으로 모든 서비스를 이용할 수 있는 유저를 조회하는 메소드
	 * @return 현재 접속한 jwt 기반 ACTIVATED 상태인 USER
	 */
	public User getCurrentActivatedUser() {
		User user = userRepository.findByUsername(jwtTokenProvider.getUsername())
			.orElseThrow(() -> new CustomException(ErrorCode.INVALID_USER));

		if (user.getUserState() != UserState.ACTIVATED) {
			throw new CustomException(ErrorCode.INVALID_USER_STATE);
		}

		return user;
	}
}

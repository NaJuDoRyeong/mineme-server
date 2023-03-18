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

import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor
public abstract class AuthService<T> {

	protected JwtTokenProvider jwtTokenProvider;
	protected UserRepository userRepository;
	protected Properties properties;

	public abstract Auth.Jwt getUserDetails(T dto);

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
}

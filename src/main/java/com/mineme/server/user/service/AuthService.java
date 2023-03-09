package com.mineme.server.user.service;

import org.springframework.stereotype.Component;

import com.mineme.server.common.enums.ErrorCode;
import com.mineme.server.common.exception.CustomException;
import com.mineme.server.entity.User;
import com.mineme.server.entity.UserMatchingCode;
import com.mineme.server.entity.enums.UserState;
import com.mineme.server.security.config.Properties;
import com.mineme.server.security.provider.JwtTokenProvider;
import com.mineme.server.user.dto.Auth;
import com.mineme.server.user.repository.UserMatchingCodeRepository;
import com.mineme.server.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public abstract class AuthService<T> {

	protected final JwtTokenProvider jwtTokenProvider;
	protected final UserRepository userRepository;
	protected final UserMatchingCodeRepository userMatchingCodeRepository;
	protected final Properties properties;

	public abstract Auth.Jwt getUserDetails(T dto);

	public User getCurrentUser() {
		return jwtTokenProvider.getUsername()
			.flatMap(username -> userRepository.findByUsername(username))
			.orElseThrow(() -> new CustomException(ErrorCode.INVALID_USER));
	}

	public void getCurrentUserState(UserState userState) {
		if (getCurrentUser().getUserState() != userState)
			throw new CustomException(ErrorCode.INVALID_USER);
	}

	public UserMatchingCode getUserMatchingCode() {
		return userMatchingCodeRepository.findByRawCode()
			.orElseThrow(() -> new CustomException(ErrorCode.SERVER_ERROR));
	}
}

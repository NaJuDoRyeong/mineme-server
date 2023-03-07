package com.mineme.server.user.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mineme.server.common.enums.ErrorCode;
import com.mineme.server.common.exception.CustomException;
import com.mineme.server.entity.User;
import com.mineme.server.security.provider.JwtTokenProvider;
import com.mineme.server.user.dto.Auth;
import com.mineme.server.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements AuthService {

	private final UserRepository userRepository;
	private final JwtTokenProvider jwtTokenProvider;

	@Transactional
	public void removeUser() {

		/* @Todo 해당 컨텍스트를 플러싱하지 않고 처리할 수 있는 방법 찾기. */
		String username = getCurrentUser().getUsername();
		User user = userRepository.findByUsername(username)
			.orElseThrow(() -> new CustomException(ErrorCode.INVALID_USER));

		userRepository.delete(user);
	}

	@Override
	public Auth.Jwt getUserDetails(Object dto) {
		return null;
	}

	@Override
	public User getCurrentUser() {
		return jwtTokenProvider.getUsername()
			.flatMap(username -> userRepository.findByUsername(username))
			.orElseThrow(() -> new CustomException(ErrorCode.INVALID_USER));
	}
}

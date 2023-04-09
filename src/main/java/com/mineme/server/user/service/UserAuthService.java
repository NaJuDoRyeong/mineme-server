package com.mineme.server.user.service;

import org.springframework.stereotype.Service;

import com.mineme.server.auth.service.AuthService;
import com.mineme.server.common.enums.ErrorCode;
import com.mineme.server.common.exception.CustomException;
import com.mineme.server.entity.User;
import com.mineme.server.entity.enums.UserState;
import com.mineme.server.security.provider.JwtTokenProvider;
import com.mineme.server.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserAuthService implements AuthService {

	private final JwtTokenProvider jwtTokenProvider;
	private final UserRepository userRepository;

	public User findByUsername(String username) {
		return userRepository.findByUsername(username).orElseThrow(() -> new CustomException(ErrorCode.INVALID_USER));
	}

	public void deleteUser(User user) {
		userRepository.delete(user);
	}

	public User saveUserDetails(User user) {
		return userRepository.save(user);
	}

	@Override
	public User getCurrentUser() {
		String username = jwtTokenProvider.getUsername();
		return userRepository.findByUsername(username).orElseThrow(() -> new CustomException(ErrorCode.INVALID_USER));
	}

	@Override
	public void isValidCurrentUserState(UserState userState) {
		if (getCurrentUser().getUserState() != userState)
			throw new CustomException(ErrorCode.INVALID_USER);
	}
}

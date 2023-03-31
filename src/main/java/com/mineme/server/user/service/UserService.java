package com.mineme.server.user.service;

import java.security.NoSuchAlgorithmException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mineme.server.auth.dto.Auth;
import com.mineme.server.auth.service.AuthService;
import com.mineme.server.common.enums.ErrorCode;
import com.mineme.server.common.exception.CustomException;
import com.mineme.server.entity.User;
import com.mineme.server.entity.UserMatchingCode;
import com.mineme.server.security.config.Properties;
import com.mineme.server.security.provider.JwtTokenProvider;
import com.mineme.server.user.dto.UserInfos;
import com.mineme.server.user.repository.UserMatchingCodeRepository;
import com.mineme.server.user.repository.UserRepository;
import com.mineme.server.user.util.UserUtil;

@Service
public class UserService extends AuthService<Object> {

	private final UserMatchingCodeRepository userMatchingCodeRepository;

	public UserService(JwtTokenProvider jwtTokenProvider, UserRepository userRepository, Properties properties,
		UserMatchingCodeRepository userMatchingCodeRepository) {
		super(jwtTokenProvider, userRepository, properties);
		this.userMatchingCodeRepository = userMatchingCodeRepository;
	}

	@Transactional
	public void removeUser() {
		/* @Todo 해당 컨텍스트를 플러싱하지 않고 처리할 수 있는 방법 찾기. */
		String username = getCurrentUser().getUsername();
		User user = userRepository.findByUsername(username)
			.orElseThrow(() -> new CustomException(ErrorCode.INVALID_USER));

		userRepository.delete(user);
	}

	public void addUserDetails(UserInfos.Init dto) {
		User currentUser = getCurrentUser();
		currentUser = UserInfos.Init.getInitializedUser(currentUser, dto);

		userRepository.save(currentUser);
	}

	public String getUserMatchingCode(User user) throws NoSuchAlgorithmException {
		UserMatchingCode tmpCode = userMatchingCodeRepository.findByUserId(user).orElse(null);

		if (tmpCode == null) {
			tmpCode = new UserMatchingCode(user);
			tmpCode = userMatchingCodeRepository.save(tmpCode);
		}

		return UserUtil.createUserCode(tmpCode.getId());
	}

	@Override
	public Auth.CreatedJwt getUserDetails(Object dto) {
		return null;
	}
}

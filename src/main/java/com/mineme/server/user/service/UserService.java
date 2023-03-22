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
import com.mineme.server.user.repository.UserMatchingCodeRepository;
import com.mineme.server.user.util.UserUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService extends AuthService<Object> {

	private final UserMatchingCodeRepository userMatchingCodeRepository;

	@Transactional
	public void removeUser() {
		/* @Todo 해당 컨텍스트를 플러싱하지 않고 처리할 수 있는 방법 찾기. */
		String username = getCurrentUser().getUsername();
		User user = userRepository.findByUsername(username)
			.orElseThrow(() -> new CustomException(ErrorCode.INVALID_USER));

		userRepository.delete(user);
	}

	@Transactional
	public String getUserMatchingCode(User user) throws NoSuchAlgorithmException {
		UserMatchingCode tmpCode = userMatchingCodeRepository.findByUserId(user).orElse(null);

		if(tmpCode == null){
			tmpCode = new UserMatchingCode(user);
			tmpCode = userMatchingCodeRepository.save(tmpCode);
			tmpCode = new UserMatchingCode(tmpCode.getId(), user, tmpCode.getId(), UserUtil.createUserCode(tmpCode.getId()));
		}

		return tmpCode.getEncodedCode();
	}

	@Override
	public Auth.Jwt getUserDetails(Object dto) {
		return null;
	}
}

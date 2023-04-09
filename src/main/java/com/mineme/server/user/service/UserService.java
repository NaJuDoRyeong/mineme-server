package com.mineme.server.user.service;

import java.security.NoSuchAlgorithmException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mineme.server.entity.User;
import com.mineme.server.entity.UserMatchingCode;
import com.mineme.server.user.dto.UserInfos;
import com.mineme.server.user.repository.UserMatchingCodeRepository;
import com.mineme.server.user.util.UserUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserMatchingCodeRepository userMatchingCodeRepository;
	private final UserAuthService userAuthService;

	@Transactional
	public void removeUser() {
		/* @Todo 해당 컨텍스트를 플러싱하지 않고 처리할 수 있는 방법 찾기. */
		String username = userAuthService.getCurrentUser().getUsername();
		User user = userAuthService.findByUsername(username);

		userAuthService.deleteUser(user);
	}

	public void addUserDetails(UserInfos.Init dto) {
		User currentUser = userAuthService.getCurrentUser();
		currentUser = UserInfos.Init.getInitializedUser(currentUser, dto);

		userAuthService.saveUserDetails(currentUser);
	}

	public String getUserMatchingCode(User user) throws NoSuchAlgorithmException {
		UserMatchingCode tmpCode = userMatchingCodeRepository.findByUserId(user).orElse(null);

		if (tmpCode == null) {
			tmpCode = new UserMatchingCode(user);
			tmpCode = userMatchingCodeRepository.save(tmpCode);
		}

		return UserUtil.createUserCode(tmpCode.getId());
	}
}

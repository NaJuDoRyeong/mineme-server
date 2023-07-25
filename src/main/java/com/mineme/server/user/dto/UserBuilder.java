package com.mineme.server.user.dto;

import com.mineme.server.auth.dto.Auth;
import com.mineme.server.entity.User;
import com.mineme.server.entity.enums.Provider;
import com.mineme.server.entity.enums.UserState;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserBuilder {

	private static final String PENDING_USER_NICKNAME = "PENDING_USER";

	/**
	 * 유저 최초 가입
	 * @return User
	 */
	public static User toPendingUserEntity(String username, Auth.SignRequest dto) {
		return User.userRegisterBuilder()
			.userCode(null)
			.username(username)
			.nickname(PENDING_USER_NICKNAME)
			.provider(Provider.of(dto.getProviderType()))
			.userState(UserState.PENDING)
			.build();
	}

	/**
	 * 유저 초기 설정
	 * @return User
	 */
	public static User toInitializedUserEntity(User user, UserInfos.Init init) {
		return User.userInitializeBuilder()
			.user(user)
			.init(init)
			.build();
	}
}

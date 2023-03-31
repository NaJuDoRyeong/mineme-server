package com.mineme.server.user.dto;

import java.time.LocalDate;

import com.mineme.server.auth.dto.Auth;
import com.mineme.server.entity.User;
import com.mineme.server.entity.enums.Provider;
import com.mineme.server.entity.enums.UserState;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserBuilder {

	public static User toPendingUserEntity(String username, Auth.SignRequest dto) {
		return User.userRegisterBuilder()
			.userCode(null)
			.username(username)
			.nickname(dto.getUsername())
			.provider(Provider.of(dto.getProviderType()))
			.userState(UserState.PENDING)
			.build();
	}

	public static User toInitializedUserEntity(User user, UserInfos.Init init) {
		return User.userInitializeBuilder()
			.userCode(user.getUserCode())
			.username(user.getUsername())
			.lastLogin(user.getLastLogin())
			.nickname(init.getNickname())
			.provider(user.getProvider())
			.userState(UserState.PENDING)
			.birthday(LocalDate.parse(init.getBirthday()))
			.gender(Character.toUpperCase(init.getGender().charAt(0)))
			.build();
	}
}

package com.mineme.server.user.service;

import java.security.NoSuchAlgorithmException;

import com.mineme.server.entity.User;
import com.mineme.server.entity.enums.UserState;
import com.mineme.server.user.dto.UserInfos;

public interface UserService {

	User getCurrentUser();

	User getCurrentActivatedUser();

	void removeUser();

	void addUserDetails(UserInfos.Init dto);

	String getUserMatchingCode(User user);

	UserInfos.Notice modifyUserNotice(UserInfos.Notice notice);

	void isValidCurrentUserState(UserState userState);
}

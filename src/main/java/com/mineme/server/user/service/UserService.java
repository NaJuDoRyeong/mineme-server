package com.mineme.server.user.service;

import com.mineme.server.entity.User;
import com.mineme.server.entity.enums.UserState;

public interface UserService {

	User getCurrentUser();
	User getCurrentActivatedUser();
	void isValidCurrentUserState(UserState userState);
}

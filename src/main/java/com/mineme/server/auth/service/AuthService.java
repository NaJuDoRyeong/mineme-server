package com.mineme.server.auth.service;

import com.mineme.server.entity.User;
import com.mineme.server.entity.enums.UserState;

public interface AuthService {

	User getCurrentUser();

	void isValidCurrentUserState(UserState userState);
}

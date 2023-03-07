package com.mineme.server.user.service;

import com.mineme.server.entity.User;
import com.mineme.server.user.dto.Auth;

public interface AuthService<T> {
	Auth.Jwt getUserDetails(T dto);

	User getCurrentUser();
}

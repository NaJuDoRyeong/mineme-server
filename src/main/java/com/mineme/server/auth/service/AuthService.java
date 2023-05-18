package com.mineme.server.auth.service;

import com.mineme.server.auth.dto.Auth;

public interface AuthService<T> {
	Auth.CreatedJwt getUserDetails(T dto);
}

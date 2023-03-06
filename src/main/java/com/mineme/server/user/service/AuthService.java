package com.mineme.server.user.service;

import com.mineme.server.user.dto.Auth;

public interface AuthService {

	Auth.Jwt getUserDetails(Auth.SignRequest dto);
}

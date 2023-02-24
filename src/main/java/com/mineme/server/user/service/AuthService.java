package com.mineme.server.user.service;

import com.mineme.server.user.dto.UserDto;

public interface AuthService {

	UserDto.Jwt getUserDetails(UserDto.SignRequest dto);
}

package com.mineme.server.user.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mineme.server.common.dto.ResponseDto;
import com.mineme.server.user.dto.UserDto;
import com.mineme.server.user.service.AppleAuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class AppleAuthController {

	private final AppleAuthService appleAuthService;

	@PostMapping("/auth/apple")
	public ResponseDto<UserDto.Jwt> appleUserDetails(@RequestBody UserDto.AppleSignRequest dto) {
		UserDto.Jwt response = appleAuthService.getUserDetails(dto);

		return new ResponseDto<>(response);
	}
}

package com.mineme.server.user.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mineme.server.common.dto.ResponseDto;
import com.mineme.server.user.dto.Apple;
import com.mineme.server.user.dto.Auth;
import com.mineme.server.user.service.AppleAuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AppleAuthController {

	private final AppleAuthService appleAuthService;

	@PostMapping("/apple")
	public ResponseDto<Auth.Jwt> appleUserDetails(@RequestBody Apple.SignRequest dto) {
		Auth.Jwt response = appleAuthService.getUserDetails(dto);

		return new ResponseDto<>(response);
	}

	@PostMapping("/apple/refresh")
	public ResponseDto appleTokenDetails(@RequestBody Apple.SignRequest dto) {
		Apple.TokenResponse response = appleAuthService.getSession(dto);

		return new ResponseDto<>(response);
	}
}

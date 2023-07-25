package com.mineme.server.auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mineme.server.common.dto.ResponseDto;
import com.mineme.server.auth.dto.Apple;
import com.mineme.server.auth.dto.Auth;
import com.mineme.server.auth.service.AppleAuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AppleAuthController {

	private final AppleAuthService appleAuthService;

	@PostMapping("/apple")
	public ResponseEntity<ResponseDto<Auth.Jwt>> appleUserDetails(@RequestBody Apple.SignRequest dto) {
		Auth.CreatedJwt response = appleAuthService.getUserDetails(dto);

		if(response.isCreatedNow())
			return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDto(response.getJwt()));

		return ResponseEntity.ok().body(new ResponseDto<>(response.getJwt()));
	}

	@PostMapping("/apple/refresh")
	public ResponseDto appleTokenDetails(@RequestBody Apple.SignRequest dto) {
		Apple.TokenResponse response = appleAuthService.getSession(dto);

		return new ResponseDto<>(response);
	}
}

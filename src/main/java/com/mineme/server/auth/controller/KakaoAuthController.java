package com.mineme.server.auth.controller;

import com.mineme.server.common.dto.ResponseDto;
import com.mineme.server.auth.dto.Auth;
import com.mineme.server.auth.service.KakaoAuthService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class KakaoAuthController {

	private final KakaoAuthService kakaoAuthService;

	@PostMapping("/kakao")
	public ResponseDto<Auth.Jwt> kakaoUserDetails(@RequestBody Auth.SignRequest dto) {
		Auth.Jwt response = kakaoAuthService.getUserDetails(dto);

		return new ResponseDto<>(response);
	}
}

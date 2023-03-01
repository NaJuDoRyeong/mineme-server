package com.mineme.server.user.controller;

import com.mineme.server.common.dto.ResponseDto;
import com.mineme.server.user.dto.UserDto;
import com.mineme.server.user.service.KakaoAuthService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class KakaoAuthController {

	private final KakaoAuthService kakaoAuthService;

	@PostMapping("/kakao")
	public ResponseDto<UserDto.Jwt> kakaoUserDetails(@RequestBody UserDto.SignRequest dto) {
		UserDto.Jwt response = kakaoAuthService.getUserDetails(dto);

		return new ResponseDto<>(response);
	}
}

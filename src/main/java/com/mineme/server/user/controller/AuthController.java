package com.mineme.server.user.controller;

import com.mineme.server.common.dto.ResponseDto;
import com.mineme.server.user.dto.UserJwtDto;
import com.mineme.server.user.dto.UserSignRequestDto;
import com.mineme.server.user.service.KakaoAuthService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class AuthController {

	private final KakaoAuthService kakaoAuthService;

	@PostMapping("user")
	public ResponseEntity<ResponseDto<UserJwtDto>> kakaoUserDetails(@RequestBody UserSignRequestDto dto) {
		UserJwtDto response = kakaoAuthService.getKakaoUserDetails(dto);

		return ResponseEntity.ok().body(new ResponseDto<>(response));
	}
}

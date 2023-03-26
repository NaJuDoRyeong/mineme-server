package com.mineme.server.auth.controller;

import com.mineme.server.common.dto.ResponseDto;
import com.mineme.server.auth.dto.Auth;
import com.mineme.server.auth.service.KakaoAuthService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class KakaoAuthController {

	private final KakaoAuthService kakaoAuthService;

	@PostMapping("/kakao")
	public ResponseEntity<ResponseDto<Auth.Jwt>> kakaoUserDetails(@RequestBody Auth.SignRequest dto) {
		Auth.CreatedJwt response = kakaoAuthService.getUserDetails(dto);

		if(response.isCreatedNow())
			return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDto(response.getJwt()));

		return ResponseEntity.ok().body(new ResponseDto<>(response.getJwt()));
	}
}

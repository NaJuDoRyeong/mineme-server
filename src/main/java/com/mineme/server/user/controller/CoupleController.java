package com.mineme.server.user.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mineme.server.common.dto.ResponseDto;
import com.mineme.server.user.service.CoupleService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/couple")
public class CoupleController {

	private final CoupleService coupleService;

	@PostMapping
	public ResponseDto coupleMatchingCode(@RequestBody String dto) {

		coupleService.addCouple(dto);

		return new ResponseDto<>(null);
	}
}

package com.mineme.server.user.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mineme.server.common.dto.ResponseDto;
import com.mineme.server.user.service.CoupleServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/couple")
public class CoupleController {

	private final CoupleServiceImpl coupleServiceImpl;

	/**
	 * 커플 매칭을 수행함
	 */
	@PostMapping
	public ResponseDto coupleMatching(@RequestBody String dto) {

		coupleServiceImpl.addUserRelationByCouple(dto);

		return new ResponseDto<>(null);
	}
}

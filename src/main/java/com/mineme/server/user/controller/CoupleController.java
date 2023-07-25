package com.mineme.server.user.controller;

import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mineme.server.common.dto.ResponseDto;
import com.mineme.server.user.dto.UserInfos;
import com.mineme.server.user.service.CoupleService;
import com.mineme.server.user.service.CoupleServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/couple")
public class CoupleController {

	private final CoupleService coupleService;

	/**
	 * 커플 매칭을 수행함
	 */
	@PostMapping
	public ResponseDto coupleMatching(@RequestBody String dto) {

		coupleService.addUserRelationByCouple(dto);

		return new ResponseDto<>(null);
	}

	/**
	 * 커플의 프로필 수정
	 */
	@PatchMapping
	public ResponseDto coupleModifying(@RequestBody UserInfos.Modifying dto) {

		UserInfos.Modifying modified = coupleService.modifyCoupleProfile(dto);

		return new ResponseDto<>(modified);
	}
}

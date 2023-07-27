package com.mineme.server.user.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mineme.server.common.dto.ResponseDto;
import com.mineme.server.user.dto.UserInfos;
import com.mineme.server.user.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

	private final UserService userService;

	/**
	 * 회원가입 직후, 최초 정보 기입
	 */
	@PostMapping
	public ResponseDto userAdd(@RequestBody UserInfos.Init dto) {
		userService.addUserDetails(dto);

		return new ResponseDto<>(null);
	}

	/**
	 * 회원 탈퇴
	 */
	@DeleteMapping
	public ResponseDto userRemove() {
		userService.removeUser();

		return new ResponseDto<>(null);
	}

	/**
	 * 사용자 알림 On/Off
	 */
	@PatchMapping("/notice")
	public ResponseDto userNoticeModify(@RequestBody UserInfos.Notice dto) {
		UserInfos.Notice response = userService.modifyUserNotice(dto);

		return new ResponseDto<>(response);
	}

}

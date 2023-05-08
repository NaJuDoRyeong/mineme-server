package com.mineme.server.user.controller;

import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mineme.server.common.dto.ResponseDto;
import com.mineme.server.user.dto.UserInfos;
import com.mineme.server.user.service.UserServiceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notice")
public class UserNoticeController {

	private final UserServiceImpl userServiceImpl;

	@PatchMapping
	public ResponseDto userNoticeModify(@RequestBody UserInfos.Notice dto) {
		userServiceImpl.modifyUserNotice(dto);

		return new ResponseDto<>(null);
	}
}

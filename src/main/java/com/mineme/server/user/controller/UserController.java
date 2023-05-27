package com.mineme.server.user.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mineme.server.common.dto.ResponseDto;
import com.mineme.server.user.dto.UserInfos;
import com.mineme.server.user.service.UserServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

	private final UserServiceImpl userService;

	@PostMapping
	public ResponseDto userAdd(@RequestBody UserInfos.Init dto) {

		userService.addUserDetails(dto);

		return new ResponseDto<>(null);
	}

	@DeleteMapping
	public ResponseDto userRemove() {

		userService.removeUser();

		return new ResponseDto<>(null);
	}
}

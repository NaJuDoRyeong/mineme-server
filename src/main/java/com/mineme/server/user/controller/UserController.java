package com.mineme.server.user.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mineme.server.common.dto.ResponseDto;
import com.mineme.server.user.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UserController {

	private final UserService userService;

	@DeleteMapping("/user")
	public ResponseDto userRemove() {

		userService.removeUser();

		return new ResponseDto<>(null);
	}
}

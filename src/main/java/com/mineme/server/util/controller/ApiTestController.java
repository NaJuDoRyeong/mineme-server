package com.mineme.server.util.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mineme.server.common.dto.ResponseDto;
import com.mineme.server.util.dto.response.DummyMainInfoResponse;

@RestController
@RequestMapping("/api/test")
public class ApiTestController {

	@GetMapping("/main/info")
	public ResponseDto<DummyMainInfoResponse> getMainInfo() {
		return new ResponseDto<DummyMainInfoResponse>(new DummyMainInfoResponse());
	}
}

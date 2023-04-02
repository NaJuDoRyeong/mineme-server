package com.mineme.server.story.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mineme.server.common.dto.ResponseDto;
import com.mineme.server.story.dto.MainStory;
import com.mineme.server.story.service.MainStoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/stories")
public class MainStoryController {

	private final MainStoryService mainStoryService;

	@GetMapping("/random")
	public ResponseDto<MainStory.Random> randomStoryDetails() {
		return new ResponseDto<>(mainStoryService.getRandomStory());
	}
}

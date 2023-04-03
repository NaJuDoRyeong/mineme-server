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

	/**
	 * 메인 페이지 랜덤 스토리 반환
	 * @return post 데이터와 post에 대한 상태를 포함한 무작위 스토리를 반환.
	 */
	@GetMapping("/random")
	public ResponseDto<MainStory.RandomStory> randomStoryDetails() {
		return new ResponseDto<>(mainStoryService.getRandomStory());
	}
}

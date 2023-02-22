package com.mineme.server.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mineme.server.common.dto.ResponseDto;
import com.mineme.server.dto.Story.Detail;
import com.mineme.server.dto.Story.SaveRequest;
import com.mineme.server.dto.Story.Stories;
import com.mineme.server.service.StoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/stories")
public class StoryController {

	private final StoryService storyService;

	/**
	 * 스토리 목록 조회
	 * TODO Paging 에 따라 파라미터가 필요할 수도 있음.
	 * @return Story 목록 및 페이징 정보
	 */
	@GetMapping("/")
	public ResponseDto<Stories> storyList() {
		return new ResponseDto<>(storyService.getStories());
	}

	/**
	 * 스토리 상세 조회
	 * TODO Paging 에 따라 파라미터가 필요할 수도 있음.
	 * @param storyId 조회할 스토리 ID
	 * @return ID 에 해당하는 스토리 및 페이징 정보
	 */
	@GetMapping("/{storyId}")
	public ResponseDto<Detail> storyDetails(@PathVariable Long storyId) {
		return new ResponseDto<>(storyService.getStory(storyId));
	}

	/**
	 * 스토리 작성
	 * @param request 작성할 스토리 정보
	 * @return data null
	 */
	@PostMapping("/")
	public ResponseDto storyAdd(@RequestBody SaveRequest request) {
		storyService.addStory(request);
		return new ResponseDto(null);
	}
}
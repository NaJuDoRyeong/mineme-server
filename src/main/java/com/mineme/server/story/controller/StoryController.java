package com.mineme.server.story.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mineme.server.common.dto.ResponseDto;
import com.mineme.server.story.dto.Story;
import com.mineme.server.story.service.StoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/stories")
public class StoryController {

	private final StoryService storyService;

	/**
	 * [GET] 스토리 목록 조회 API
	 * @param year	조회할 연도
	 * @param month	조회할 월
	 * @return		Story 목록 및 페이징 정보
	 */
	@GetMapping
	public ResponseDto<Story.Stories> storyList(@RequestParam String year, @RequestParam String month) {
		return new ResponseDto<>(storyService.getStories(year, month));
	}

	/**
	 * [GET] 스토리 상세 조회 API
	 * TODO Paging 에 따라 파라미터가 필요할 수도 있음.
	 * @param storyId	조회할 스토리 ID
	 * @return			ID 에 해당하는 스토리 및 페이징 정보
	 */
	@GetMapping("/{storyId}")
	public ResponseDto<Story.Detail> storyDetails(@PathVariable Long storyId) {
		return new ResponseDto<>(storyService.getStory(storyId));
	}

	/**
	 * [POST] 스토리 작성 API
	 * @param request	작성할 스토리 정보
	 * @return			data null
	 */
	@PostMapping
	public ResponseEntity<ResponseDto<Object>> storyAdd(@RequestBody Story.SaveRequest request) {
		storyService.addStory(request);
		return ResponseDto.createdNullableResponseDto();
	}

	/**
	 * [POST] 스토리 이미지 업로드 API
	 * @param files	이미지 파일 리스트
	 * @return		업로드된 이미지 URL 리스트
	 */
	@PostMapping("/image")
	public ResponseDto<Story.Urls> imageUpload(@RequestParam("image") List<MultipartFile> files) {
		return new ResponseDto(storyService.uploadImage(files));
	}
}
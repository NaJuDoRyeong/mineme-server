package com.mineme.server.story.service;

import org.springframework.stereotype.Service;

import com.mineme.server.story.dto.MainStory;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MainStoryService {

	public MainStory.Random getRandomStory() {
		/* 구현 중 */

		/* 테스트 데이터 */
		return MainStory.Random.toRandomStoryForTest();
	}
}

package com.mineme.server.service;

import org.springframework.stereotype.Service;

import com.mineme.server.dto.Story.Detail;
import com.mineme.server.dto.Story.SaveRequest;
import com.mineme.server.dto.Story.Stories;
import com.mineme.server.entity.User;
import com.mineme.server.repository.PostRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class StoryService {
	private final PostRepository postRepository;

	public Stories getStories() {
		// TODO jwt 이용 현재 유저 가져오기
		User currentUser = getCurrentUser();
		// TODO Paging 이용해서 목록 제공하기

		return null;
	}

	public Detail getStory(Long storyId) {
		// TODO jwt 이용 현재 유저 가져오기
		// TODO Paging 이용해서 목록 제공하기
		return new Detail();
	}

	public void addStory(SaveRequest request) {
		// TODO jwt 이용 현재 유저 가져오기
		postRepository.save(request.toEntity());
	}

	// TODO test용
	private User getCurrentUser() {
		return null;
	}
}

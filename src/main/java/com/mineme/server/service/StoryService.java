package com.mineme.server.service;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mineme.server.common.enums.ErrorCode;
import com.mineme.server.common.exception.CustomException;
import com.mineme.server.dto.Story.Detail;
import com.mineme.server.dto.Story.SaveRequest;
import com.mineme.server.dto.Story.Stories;
import com.mineme.server.entity.Post;
import com.mineme.server.entity.User;
import com.mineme.server.entity.enums.UserState;
import com.mineme.server.repository.PostRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StoryService {
	private final AuthService authService;
	private final PostRepository postRepository;

	public Stories getStories() {
		// TODO 현재 유저가 커플인지 검증 (ACTIVATED)

		User currentUser = authService.getCurrentUser();

		LocalDate start = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth());
		LocalDate end = LocalDate.now().with(TemporalAdjusters.lastDayOfMonth());
		// TODO Paging 이용해서 목록 제공하기
		List<Post> posts = postRepository.findMonthlyPosts(currentUser, start, end);

		return Stories.of(start, posts);
	}

	public Detail getStory(Long storyId) {
		// TODO Paging 이용해서 목록 제공하기
		return Detail.of(getPostAndValidate(storyId));
	}

	@Transactional
	public void addStory(SaveRequest request) {
		// TODO 현재 유저가 커플인지 검증 (ACTIVATED?).
		postRepository.save(request.toEntity(authService.getCurrentUser()));
	}

	public Post getPostAndValidate(Long postId) {
		Post post = postRepository.findById(postId)
			.orElseThrow(() -> new CustomException(ErrorCode.INVALID_REQUEST));

		User user = authService.getCurrentUser();

		if (post.getUserId().getId() != user.getId()) {
			throw new CustomException(ErrorCode.INVALID_USER);
		}
		// TODO AuthService 에 유저 유효성 로직 구현되면 변경
		// if (user.getUserState() != UserState.ACTIVATED) {
		// 	throw new CustomException(ErrorCode.INVALID_USER);
		// }

		return post;
	}
}

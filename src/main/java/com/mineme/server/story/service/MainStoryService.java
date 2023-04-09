package com.mineme.server.story.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mineme.server.auth.service.AuthService;
import com.mineme.server.common.enums.ErrorCode;
import com.mineme.server.common.exception.CustomException;
import com.mineme.server.entity.Couple;
import com.mineme.server.entity.Post;
import com.mineme.server.entity.User;
import com.mineme.server.entity.enums.UserState;
import com.mineme.server.repository.PostRepository;
import com.mineme.server.security.provider.JwtTokenProvider;
import com.mineme.server.story.dto.MainStory;
import com.mineme.server.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MainStoryService implements AuthService {

	private final PostRepository postRepository;
	private final JwtTokenProvider jwtTokenProvider;
	private final UserRepository userRepository;

	public MainStory.RandomStory getRandomStory() {
		User currentUser = getCurrentUser();
		isValidCurrentUserState(currentUser.getUserState());

		/* 구현 중 */
		List<Post> coupleStories = getStoriesByCouple(currentUser.getCoupleId());

		/* 테스트 데이터 */
		return MainStory.RandomStory.toRandomStoryForTest();
	}

	public List<Post> getStoriesByCouple(Couple couple) {
		return postRepository.findByCoupleId(couple);
	}

	@Override
	public User getCurrentUser() {
		String username = jwtTokenProvider.getUsername();
		return userRepository.findByUsername(username).orElseThrow(() -> new CustomException(ErrorCode.INVALID_USER));
	}

	@Override
	public void isValidCurrentUserState(UserState userState) {
		if (getCurrentUser().getUserState() != userState)
			throw new CustomException(ErrorCode.INVALID_USER);
	}
}

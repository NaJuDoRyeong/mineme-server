package com.mineme.server.story.service;

import java.util.List;
import java.util.Optional;

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
import com.mineme.server.story.dto.enums.RandomStoryStatus;
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
		Optional<Post> coupleStory = getRandomStoryByCouple(currentUser.getCoupleId());

		if(coupleStory.isPresent()) {
			return MainStory.RandomStory.toRandomStory(coupleStory.get());
		}

		return new MainStory.RandomStory(RandomStoryStatus.NO_LATEST_DATA);

		/* 테스트 데이터 */
		// return MainStory.RandomStory.toRandomStoryForTest();
	}

	public List<Post> getStoriesByCouple(Couple couple) {
		return postRepository.findByCoupleId(couple);
	}

	public Optional<Post> getRandomStoryByCouple(Couple couple) {
		List<Post> stories = getStoriesByCouple(couple);

		if (stories.isEmpty()) {
			return Optional.ofNullable(null);
		}

		int idx = (int)((Math.random() * 10000) % stories.size());
		return Optional.of(stories.get(idx));
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

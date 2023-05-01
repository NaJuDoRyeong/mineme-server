package com.mineme.server.story.service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeParseException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.mineme.server.common.enums.ErrorCode;
import com.mineme.server.common.exception.CustomException;
import com.mineme.server.common.file.S3Uploader;
import com.mineme.server.story.dto.Story;
import com.mineme.server.entity.Post;
import com.mineme.server.entity.User;
import com.mineme.server.story.repository.PostRepository;
import com.mineme.server.service.AuthService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StoryService {

	private final AuthService authService;
	private final PostRepository postRepository;
	private final S3Uploader s3Uploader;

	public Story.Stories getStories(String year, String month) {
		User currentUser = authService.getCurrentActivatedUser();
		YearMonth yearMonth = parseToYearMonth(year, month);

		List<Post> posts = postRepository.findMonthlyPosts(currentUser, yearMonth.atDay(1), yearMonth.atEndOfMonth());

		return Story.Stories.of(yearMonth.atDay(1), posts);
	}

	public Story.Detail getStory(Long storyId) {
		// TODO Paging 이용해서 목록 제공하기
		return Story.Detail.of(getPostAndValidate(storyId));
	}

	@Transactional
	public void addStory(Story.SaveRequest request) {
		postRepository.save(
			Post.createPost(request, authService.getCurrentActivatedUser())
		);
	}

	public Story.Urls uploadImage(List<MultipartFile> files) {
		User user = authService.getCurrentActivatedUser();
		List<String> urls = s3Uploader.uploadFiles(files, user.getId().toString());
		return new Story.Urls(urls);
	}

	private Post getPostAndValidate(Long postId) {
		Post post = postRepository.findById(postId)
			.orElseThrow(() -> new CustomException(ErrorCode.INVALID_REQUEST));

		User user = authService.getCurrentActivatedUser();

		if (!post.getUserId().getId().equals(user.getId())) {
			throw new CustomException(ErrorCode.INVALID_USER);
		}

		return post;
	}

	private YearMonth parseToYearMonth(String year, String month) {
		try {
			LocalDate date = LocalDate.parse(year + "-" + month + "-01");
			return YearMonth.from(date);
		} catch (DateTimeParseException e) {
			return YearMonth.from(LocalDate.now());
		}
	}
}

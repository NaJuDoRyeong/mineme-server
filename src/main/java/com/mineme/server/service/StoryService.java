package com.mineme.server.service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.mineme.server.common.enums.ErrorCode;
import com.mineme.server.common.exception.CustomException;
import com.mineme.server.common.file.S3Uploader;
import com.mineme.server.dto.Story;
import com.mineme.server.entity.Post;
import com.mineme.server.entity.User;
import com.mineme.server.repository.PostRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StoryService {
	private static final String START_DATE = "START";
	private static final String END_DATE = "END";
	private final AuthService authService;
	private final PostRepository postRepository;
	private final S3Uploader s3Uploader;

	public Story.Stories getStories(String year, String month) {
		User currentUser = authService.getCurrentActivatedUser();
		Map<String, LocalDate> date = parseToLocalDateMap(year, month);
		List<Post> posts = postRepository.findMonthlyPosts(currentUser, date.get(START_DATE), date.get(END_DATE));

		return Story.Stories.of(date.get(START_DATE), posts);
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

	private Map<String, LocalDate> parseToLocalDateMap(String year, String month) {
		try {
			Map<String, LocalDate> dateMap = new HashMap<>();

			LocalDate date = LocalDate.parse(year + "-" + month + "-01");
			YearMonth yearMonth = YearMonth.from(date);
			dateMap.put(START_DATE, yearMonth.atDay(1));
			dateMap.put(END_DATE, yearMonth.atEndOfMonth());
			return dateMap;
		} catch (DateTimeParseException e) {
			throw new CustomException(ErrorCode.INVALID_DATE_FORMAT);
		}
	}
}

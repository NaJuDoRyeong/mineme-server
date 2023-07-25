package com.mineme.server.story.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import com.mineme.server.entity.Photo;
import com.mineme.server.entity.Post;
import com.mineme.server.entity.enums.RegionCode;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Story {

	/**
	 * 목록 조회 Response
	 */
	@Getter
	@AllArgsConstructor
	public static class Stories {
		private List<MonthInfo> stories;

		@Getter
		@AllArgsConstructor
		@Builder
		static class MonthInfo {
			private String year;
			private String month;
			private List<PostInfo> posts;

			@Getter
			static class PostInfo {
				private RegionCode region;
				private LocalDate date;
				private String thumbnail;
				private Long postId;

				public PostInfo(Post post) {
					// TODO Region Code 로직 수정
					this.region = RegionCode.GYEONGNAM_CHANGWON;
					this.date = post.getDatedAt();
					this.thumbnail = post.getPhotos().stream()
						.filter(Photo::isThumbnail)
						.map(Photo::getPhotoUrl)
						.findFirst()
						.orElse(null);
					this.postId = post.getId();
				}
			}
		}

		public static Stories of(LocalDate start, List<Post> posts) {
			return new Stories(
				List.of(
					MonthInfo.builder()
						.year(String.valueOf(start.getYear()))
						.month(start.getMonth().name())
						.posts(
							posts.stream()
								.map(MonthInfo.PostInfo::new)
								.collect(Collectors.toList())
						)
						.build()
				)
			);
		}
	}

	/**
	 * 상세 조회 Response
	 */
	@Getter
	@AllArgsConstructor
	public static class Detail {
		private List<StoryDetail> stories;

		@Getter
		static class StoryDetail {
			private RegionCode region;
			private Character isAnniversary;
			private Anniversary anniversary;
			private LocalDate date;
			private List<String> images;
			private Long postId;
			private String content;
			private String author;

			@Getter
			static class Anniversary {
				// TODO Type 을 String 으로 할지, ENUM 으로 처리할지
				private String type;
				private Integer day;
			}

			public StoryDetail(Post post) {
				// TODO 기념일 설정 여부 요구사항 나오는대로 구현 예정
				this.region = RegionCode.GYEONGNAM_CHANGWON;
				this.isAnniversary = 'n';
				// this.anniversary = anniversary;
				this.date = post.getDatedAt();
				this.images = post.getPhotos().stream()
					.map(Photo::getPhotoUrl)
					.collect(Collectors.toList());
				this.postId = post.getId();
				this.content = post.getContent();
				this.author = post.getUserId().getNickname();
			}
		}

		// TODO 무한스크롤 구현 시 Post List 를 파라미터로 받도록 변경
		public static Detail of(Post post) {
			return new Detail(
				List.of(
					new StoryDetail(post)
				)
			);
		}
	}

	/**
	 * 작성 Request
	 */
	@Getter
	public static class SaveRequest {
		// TODO Validation 검증
		private LocalDate date;
		private List<String> images;
		private Integer thumbnail;
		private String content;
		private RegionCode region;
	}

	/**
	 * 이미지 업로드 Response
	 */
	@Getter
	public static class Urls {
		private List<String> images;

		public Urls(List<String> images) {
			this.images = images;
		}
	}
}

package com.mineme.server.dto;

import java.time.LocalDate;
import java.util.List;

import com.mineme.server.entity.Post;
import com.mineme.server.entity.enums.RegionCode;

import lombok.Getter;

@Getter
public class Story {

	/**
	 * 목록 조회 Response
	 */
	@Getter
	public static class Stories {
		private List<MonthInfo> stories;

		@Getter
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
			}
		}

	}

	/**
	 * 상세 조회 Response
	 */
	@Getter
	public static class Detail {
		private List<StoryDetail> stories;

		@Getter
		static class StoryDetail {
			private RegionCode region;
			private char isAnniversary;
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
				private int day;
			}
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
		private int thumbnail;
		private String content;
		private RegionCode region;

		public Post toEntity() {
			return Post.builder()
				.datedAt(getDate().atStartOfDay())
				.content(getContent())
				.build();
		}
	}
}

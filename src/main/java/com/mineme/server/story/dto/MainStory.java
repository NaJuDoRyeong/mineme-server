package com.mineme.server.story.dto;

import java.time.LocalDate;

import com.mineme.server.entity.Post;
import com.mineme.server.story.dto.enums.RandomStoryStatus;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MainStory {

	@Getter
	public static class Random {

		private String isExist;
		private RandomStoryStatus status;
		private RandomStory post;

		/* 조회할 랜덤 스토리가 있는 경우 */
		public Random(RandomStoryStatus status, final Post post) {
			this.isExist = "y";
			this.status = status;
			this.post = toRandomStoryDetailsForTest();
		}

		/* 조회할 랜덤 스토리가 없을 경우 */
		public Random(RandomStoryStatus status) {
			this.isExist = "n";
			this.status = status;
			this.post = null;
		}

		@Getter
		static class RandomStory {
			private Long postId;
			private String title;
			private String thumbnail;
			private LocalDate date;

			@Builder(builderClassName = "mainRandomStoryBuilder", builderMethodName = "mainRandomStoryBuilder")
			public RandomStory(Long postId, String title, String thumbnail, LocalDate date) {
				this.postId = postId;
				this.title = title;
				this.thumbnail = thumbnail;
				this.date = date;
			}
		}

		public static RandomStory toRandomStoryDetails(final Post post) {
			return RandomStory.mainRandomStoryBuilder()
				.postId(post.getId())
				.title("")
				.thumbnail(post.getPhotos().get(0).getPhotoUrl())
				.date(post.getDatedAt())
				.build();
		}

		/*
			@See
			여기서 부터 테스트용
		 */

		public static Random toRandomStoryForTest() {
			int num = (int)((Math.random()*10000)%10);

			if(num < 3) {
				return new Random(RandomStoryStatus.NO_LATEST_DATA);
			} else if ( 3 <= num && num < 5) {
				return new Random(RandomStoryStatus.NO_DATA);
			} else if ( 5 <= num && num < 8) {
				return new Random(RandomStoryStatus.ANNIVERSARY, null);
			} else {
				return new Random(RandomStoryStatus.RANDOM, null);
			}
		}

		public static RandomStory toRandomStoryDetailsForTest() {
			int num = (int)((Math.random()*10000)%10);

			if (num < 5) {
				return RandomStory.mainRandomStoryBuilder()
					.postId(101L)
					.title("37일 전 파리에서")
					.thumbnail(
						"https://ssl.pstatic.net/melona/libs/1441/1441486/aa2c50ca3df7945375dc_20230323115936075.png")
					.date(LocalDate.now())
					.build();
			} else {
				return RandomStory.mainRandomStoryBuilder()
					.postId(100L)
					.title("1년 전 오늘")
					.thumbnail(
						"https://ssl.pstatic.net/melona/libs/1441/1441486/aa2c50ca3df7945375dc_20230323115936075.png")
					.date(LocalDate.now())
					.build();
			}
		}
	}
}

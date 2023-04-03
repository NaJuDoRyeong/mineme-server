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
	public static class RandomStory {

		private String isExist;
		private RandomStoryStatus status;
		private RandomStoryDetails post;

		public static RandomStory getMainRandomStory(RandomStoryStatus status, final Post post) {
			try {
				return new RandomStory(status, post);
			} catch (NullPointerException e) { // post에서 NPE가 발생할 경우 대응.
				return new RandomStory(RandomStoryStatus.NO_DATA);
			}
		}

		/* 조회할 랜덤 스토리가 있는 경우 */
		public RandomStory(RandomStoryStatus status, final Post post) {
			this.isExist = "y";
			this.status = status;
			this.post = toRandomStoryDetailsForTest();
		}

		/* 조회할 랜덤 스토리가 없을 경우 */
		public RandomStory(RandomStoryStatus status) {
			this.isExist = "n";
			this.status = status;
			this.post = null;
		}

		@Getter
		static class RandomStoryDetails {
			private Long postId;

			/* Post의 title이 아닌 Main 화면의 RandomStory 타이틀로 사용됨. */
			private String title;
			private String thumbnail;
			private LocalDate date;

			@Builder(builderClassName = "mainRandomStoryDetailsBuilder", builderMethodName = "mainRandomStoryDetailsBuilder")
			public RandomStoryDetails(Long postId, String title, String thumbnail, LocalDate date) {
				this.postId = postId;
				this.title = title;
				this.thumbnail = thumbnail;
				this.date = date;
			}
		}

		/* 메인 랜덤 스토리 세부 정보 */
		public static RandomStoryDetails toRandomStoryDetails(final Post post) throws NullPointerException {
			return RandomStoryDetails.mainRandomStoryDetailsBuilder()
				.postId(post.getId())
				.title("") //@Todo 기준에 따라 별도의 문자열을 생성하는 메서드가 필요함.
				.thumbnail(post.getPhotos().get(0).getPhotoUrl())
				.date(post.getDatedAt())
				.build();
		}

		/* @Todo 여기서부터 테스트 용이므로 추후 삭제 예정 */

		public static RandomStory toRandomStoryForTest() {
			int num = (int)((Math.random() * 10000) % 10);

			if (num < 2) {
				return new RandomStory(RandomStoryStatus.NO_LATEST_DATA);
			} else if (2 <= num && num < 4) {
				return new RandomStory(RandomStoryStatus.NO_DATA);
			} else if (4 <= num && num < 8) {
				return getMainRandomStory(RandomStoryStatus.ANNIVERSARY, null);
			} else {
				return getMainRandomStory(RandomStoryStatus.RANDOM, null);
			}
		}

		public static RandomStoryDetails toRandomStoryDetailsForTest() {
			int num = (int)((Math.random() * 10000) % 10);

			if (num < 5) {
				return RandomStoryDetails.mainRandomStoryDetailsBuilder()
					.postId(101L)
					.title("37일 전 파리에서")
					.thumbnail(
						"https://ssl.pstatic.net/melona/libs/1441/1441486/aa2c50ca3df7945375dc_20230323115936075.png")
					.date(LocalDate.now())
					.build();
			} else {
				return RandomStoryDetails.mainRandomStoryDetailsBuilder()
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

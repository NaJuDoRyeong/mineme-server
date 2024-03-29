package com.mineme.server.user.dto;

import java.time.format.DateTimeParseException;

import com.mineme.server.common.enums.ErrorCode;
import com.mineme.server.common.exception.CustomException;
import com.mineme.server.entity.User;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserInfos {

	@Getter
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class Init {
		private String nickname;
		private String birthday;

		public Init(String nickname, String birthday) {
			this.nickname = nickname;
			this.birthday = birthday;
		}

		public static User getInitializedUser(User user, Init init) {
			try {
				return UserBuilder.toInitializedUserEntity(user, init);
			} catch (DateTimeParseException e) {
				throw new CustomException(ErrorCode.INVALID_DATE_FORMAT);
			}
		}
	}

	@Getter
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class Notice {
		private String type;
		private String allow;

		public Notice(String type, String allow) {
			this.type = type;
			this.allow = allow;
		}

		public Notice(String notice, User user) {
			this.type = notice;

			if(notice.equals("ANNIVERSARY")) {
				if(user.getNoticeAnniversary())
					this.allow = "y";
				else
					this.allow = "n";
			} else if (notice.equals("FEED")) {
				if(user.getNoticeAnniversary())
					this.allow = "y";
				else
					this.allow = "n";
			} else if (notice.equals("MARKETING")) {
				if(user.getNoticeFeed())
					this.allow = "y";
				else
					this.allow = "n";
			} else {
				if(user.getNoticeMarketing())
					this.allow = "y";
				else
					this.allow = "n";
			}
		}
	}
}

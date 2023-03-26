package com.mineme.server.user.dto;

import java.time.LocalDate;
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
		private String gender;

		public Init(String nickname, String birthday, String gender) {
			this.nickname = nickname;
			this.birthday = birthday;
			this.gender = gender;
		}

		public static User getInitializedUser(User user, Init init) {
			try {
				char gender = Character.toUpperCase(init.getGender().charAt(0));
				if (gender != 'M' && gender != 'F')
					throw new CustomException(ErrorCode.INVALID_GENDER_FORMAT);

				return new User(null, user.getUserCode(), user.getUsername(), init.getNickname(), user.getUserState(),
					user.getProvider(), user.getProfileImageUrl(), user.getEmail(), user.getLastLogin(),
					LocalDate.parse(init.getBirthday()), user.getComment(), gender,
					user.getInstaId(), user.getDeviceToken(), user.getDevice(), user.getPhoneNumber(),
					user.getExtraValues(), user.getNoticeFeed(), user.getNoticeAnniversary(),
					user.getNoticeMarketing());
			} catch (DateTimeParseException e) {
				throw new CustomException(ErrorCode.INVALID_DATE_FORMAT);
			}
		}
	}
}

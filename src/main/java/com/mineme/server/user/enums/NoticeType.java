package com.mineme.server.user.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public enum NoticeType {
	ANNIVERSARY("ANNIVERSARY"), FEED("FEED"), MARKETING("MARKETING");

	private String type;
}

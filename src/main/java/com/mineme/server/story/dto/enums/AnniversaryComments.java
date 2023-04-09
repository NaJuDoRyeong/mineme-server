package com.mineme.server.story.dto.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum AnniversaryComments {
	CHRISTMAS("크라스마스"),
	YEARS_AGO("년 전 오늘"),
	HUNDRED("00일 우리");

	@Getter
	private String comment;
}

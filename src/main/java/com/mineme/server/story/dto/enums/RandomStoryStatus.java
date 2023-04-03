package com.mineme.server.story.dto.enums;

public enum RandomStoryStatus {
	NO_DATA, // 작성한 게시글이 없을 경우
	NO_LATEST_DATA, // 최근 2주 이내에 작성한 게시글이 없을 경우
	RANDOM, // 기념일을 제외한 무작위로 뽑은 게시글일 경우
	ANNIVERSARY // 특정 기념일에 대한 게시글일 경우
}

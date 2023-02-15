package com.mineme.server.util.dto.response;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
public class DummyMainInfoResponse {
	private final Couple couple = DummyCouple();
	private final Story newStory = DummyNewStory();
	private final List<Widget> widgets = DummyWidgets();

	private Couple DummyCouple() {
		return Couple.builder()
			.name("알콩달콩")
			.startDate(LocalDate.of(2022, 12, 25))
			.me(
				Couple.Person.builder()
					.profileImage("https://upload3.inven.co.kr/upload/2022/03/15/bbs/i16343629296.jpg?MW=800")
					.nickname("윈터")
					.description("나는 윈터")
					.instaId("ol_i0_")
					.birthday(LocalDate.of(2001, 1, 1))
					.gender("M")
					.build()
			)
			.mine(
				Couple.Person.builder()
					.profileImage("https://mblogthumb-phinf.pstatic.net/MjAyMjA4MDdfMzQg/MDAxNjU5ODA4NDg3NDk2.8HhF0w7-181YY123fVFGM6GbUpjT56nCSp7Vc5-5RkIg.3wEL822sJQDFf8tJrhaRFIYaXB8FL8PFqCCNCWR3yAkg.JPEG.niceguy00/Seul_%EC%97%90%EC%8A%A4%ED%8C%8C_%EB%8F%84%EA%B9%A8%EB%B9%84%EB%B6%88_%EC%B9%B4%EB%A6%AC%EB%82%9879.jpg?type=w800")
					.nickname("카리나")
					.description("나는 카리나")
					.instaId("ol_i0_")
					.birthday(LocalDate.of(2000, 4, 11))
					.gender("F")
					.build()
			)
			.build();
	}

	private Story DummyNewStory() {
		return Story.builder()
			.postId(427L)
			.region("MR427")
			.date(LocalDate.of(2022, 12, 25))
			.thumbnailImage(
				"https://images.unsplash.com/photo-1511739001486-6bfe10ce785f?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=987&q=80")
			.build();
	}

	private List<Widget> DummyWidgets() {
		List<Widget> widgets = new ArrayList<>();

		Widget<DdayWidget> ddayWidget = Widget.<DdayWidget>builder()
			.id(4L)
			.order(0)
			.type("DDAY")
			.name("디데이위젯")
			.color("BLACK")
			.width(1)
			.height(1)
			.widget(
				DdayWidget.builder()
					.date(LocalDate.of(2022, 12, 25))
					.fontStyle("DPLUS")
					.xPos("LEFT")
					.yPos("MIDDLE")
					.background(
						"https://images.unsplash.com/photo-1452457807411-4979b707c5be?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=2340&q=80")
					.build()
			)
			.build();

		Widget<MemoWidget> memoWidget = Widget.<MemoWidget>builder()
			.id(27L)
			.order(1)
			.type("MEMO")
			.name("메모위젯")
			.color("WHITE")
			.width(1)
			.height(1)
			.widget(
				MemoWidget.builder()
					.contents(
						List.of("세탁소 들리기", "인생네컷 찍기")
					)
					.build()
			)
			.build();

		widgets.add(ddayWidget);
		widgets.add(memoWidget);

		return widgets;
	}

	@Builder
	@Getter
	private static class Couple {
		private String name;
		private LocalDate startDate;
		private Person me;
		private Person mine;

		@Builder
		@Getter
		private static class Person {
			private String profileImage;
			private String nickname;
			private String description;
			private String instaId;
			private LocalDate birthday;
			private String gender;
		}
	}

	@Builder
	@Getter
	private static class Story {
		private Long postId;
		private String region;
		private LocalDate date;
		private String thumbnailImage;
	}

	@Builder
	@Getter
	private static class Widget<T> {
		private Long id;
		private int order;
		private String type;
		private String name;
		private String color;
		private int width;
		private int height;
		private T widget;
	}

	@Builder
	@Getter
	private static class DdayWidget {
		private LocalDate date;
		private String fontStyle;
		private String xPos;
		private String yPos;
		private String background;
	}

	@Builder
	@Getter
	private static class MemoWidget {
		private List<String> contents;
	}
}

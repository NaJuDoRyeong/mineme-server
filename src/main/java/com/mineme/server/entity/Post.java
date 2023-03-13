package com.mineme.server.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity {

	@Id
	@Column(name = "POST_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID")
	private User userId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COUPLE_ID")
	private Couple coupleId;

	@OneToMany(mappedBy = "postId")
	private List<Photo> photos = new ArrayList<>();

	@Column(name = "DATED_AT")
	private LocalDate datedAt;

	@Column(name = "TITLE")
	@NotNull
	private String title;

	@Column(name = "CONTENT", columnDefinition = "TEXT")
	@NotNull
	private String content;

    @Builder
    public Post(User user, LocalDate datedAt, String title, String content) {
		this.userId = user;
		this.coupleId = user.getCoupleId();
        this.datedAt = datedAt;
        this.title = title;
        this.content = content;
    }
}

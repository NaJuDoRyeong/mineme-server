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

import com.mineme.server.dto.Story;

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

	@OneToMany(mappedBy = "postId", cascade = CascadeType.ALL)
	private List<Photo> photos = new ArrayList<>();

	@Column(name = "DATED_AT")
	private LocalDate datedAt;

	@Column(name = "CONTENT", columnDefinition = "TEXT")
	@NotNull
	private String content;

    @Builder
    public Post(User user, LocalDate datedAt, String content) {
		this.userId = user;
		this.coupleId = user.getCoupleId();
        this.datedAt = datedAt;
        this.content = content;
    }

	//==연관관계 메소드==//
	public void addPhoto(Photo photo) {
		photos.add(photo);
		photo.setPost(this);
	}

	//==생성 메소드==//
	public static Post createPost(Story.SaveRequest request, User user) {
		Post post = Post.builder()
			.user(user)
			.datedAt(request.getDate())
			.content(request.getContent())
			.build();

		List<String> images = request.getImages();
		for (int i = 0; i < images.size(); i++) {
			post.addPhoto(new Photo(images.get(i), request.getThumbnail().equals(i)));
		}

		return post;
	}
}

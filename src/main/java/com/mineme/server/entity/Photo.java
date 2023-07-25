package com.mineme.server.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Photo extends BaseEntity {

	@Id
	@Column(name = "PHOTO_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "POST_ID")
	private Post postId;

	@Column(name = "PHOTO_URL")
	@NotNull
	private String photoUrl;

	@Column(name = "THUMBNAIL", columnDefinition = "TINYINT(1)")
	@NotNull
	private boolean thumbnail;

	public Photo(String photoUrl, boolean thumbnail) {
		this.photoUrl = photoUrl;
		this.thumbnail = thumbnail;
	}

	//==연관관계 메소드==//
	public void setPost(Post post) {
		this.postId = post;
		post.getPhotos().add(this);
	}
}

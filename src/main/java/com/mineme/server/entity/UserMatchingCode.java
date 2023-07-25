package com.mineme.server.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserMatchingCode extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "USER_MATCHING_CODE_ID")
	private Long id;

	@OneToOne
	@JoinColumn(name = "USER_ID")
	private User userId;

	@Column(name = "RAW_CODE")
	private Long rawCode;

	@Column(name = "ENCODED_CODE")
	private String encodedCode;

	public UserMatchingCode(User userId) {
		this.userId = userId;
	}

	public UserMatchingCode(Long id, User userId, Long rawCode, String encodedCode) {
		this.id = id;
		this.userId = userId;
		this.rawCode = rawCode;
		this.encodedCode = encodedCode;
	}
}

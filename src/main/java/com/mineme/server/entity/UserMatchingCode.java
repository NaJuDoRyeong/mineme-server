package com.mineme.server.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SequenceGenerator(name = "user_matching_code_seq_generator", sequenceName = "user_matching_code_seq", initialValue = 100000, allocationSize = 100)
public class UserMatchingCode extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_matching_code_seq_generator")
	private Long id;

	@OneToOne
	@JoinColumn(name = "user_id")
	private User userId;

	@Column
	private Long rawCode;

	@Column
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

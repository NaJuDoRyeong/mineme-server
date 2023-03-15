package com.mineme.server.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SequenceGenerator(name = "user_matching_code_seq_generator", sequenceName = "user_matching_code_seq", allocationSize = 100)
public class UserMatchingCode extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_matching_code_seq_generator")
	private Long id;

	@Column
	private Long rawCode;

	@Column
	private String encodedCode;
}

package com.mineme.server.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Entity
@Table(name = "COUPLE_ANNIVERSARY")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CoupleAnniversary extends BaseEntity {

	@Id
	@Column(name = "COUPLE_ANNIVERSARY_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "ANNIVERSARY_TYPE")
	@Size(max = 16)
	@NotNull
	private String anniversaryType;

	@Column(name = "NAME")
	private String name;

	public CoupleAnniversary(String anniversaryType, String name) {
		this.anniversaryType = anniversaryType;
		this.name = name;
	}
}

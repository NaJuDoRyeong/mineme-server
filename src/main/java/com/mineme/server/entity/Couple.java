package com.mineme.server.entity;

import com.mineme.server.common.enums.ErrorCode;
import com.mineme.server.common.exception.CustomException;
import com.mineme.server.entity.enums.CoupleState;
import com.mineme.server.user.dto.UserInfos;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Couple extends BaseEntity {

	@Id
	@Column(name = "COUPLE_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToMany(mappedBy = "coupleId", cascade = CascadeType.ALL)
	private List<User> users = new ArrayList<>();

	@OneToMany(mappedBy = "coupleId", cascade = CascadeType.ALL)
	private List<Post> posts = new ArrayList<>();

	@OneToMany(mappedBy = "coupleId", cascade = CascadeType.ALL)
	private List<CoupleWidget> coupleWidgets = new ArrayList<>();

	@Column(name = "NAME")
	private String name;

	@Enumerated(EnumType.STRING)
	@Column(name = "COUPLE_STATE")
	@NotNull
	private CoupleState coupleState;

	@Column(name = "BEGIN_DATE")
	@NotNull
	private LocalDate beginDate;

	@Builder
	public Couple(String me, String mine) {
		this.name = me + "&" + mine;
		this.coupleState = CoupleState.ACTIVATED;
		this.beginDate = LocalDate.now();
	}

	/**
	 * 커플 프로필 중 커플에서 관리하는 값을 수정
	 * @return Couple
	 */
	public Couple updateCoupleProfile(User user, Optional<UserInfos.Modifying> dto) {
		if (users.size() > 2) {
			throw new CustomException(ErrorCode.INVALID_COUPLE_SIZE);
		}

		this.beginDate = dto.map(UserInfos.Modifying::getStartDate).orElse(this.beginDate);
		this.name = dto.map(UserInfos.Modifying::getCoupleName).orElse(this.name);

		for (int i = 0; i < users.size(); i++) {
			users.set(i, user);
		}



		return this;
	}

	/**
	 * 커플 상태를 비활성화 (Deactivate) 시킴.
	 */
	public void deactivateCouple(User user) throws NullPointerException {
		if (this.users.size() > 2) {
			throw new CustomException(ErrorCode.INVALID_COUPLE_SIZE);
		}

		for (int userIdx = 0; userIdx < this.users.size(); userIdx++) {
			if (this.users.get(userIdx).getId().equals(user.getId())) {
				this.users.remove(userIdx);
			}
		}

		this.coupleState = CoupleState.DEACTIVATED;
	}

	public static Couple getEmptyCoupleEntity(String me, String mine) {
		return Couple.builder()
			.me(me)
			.mine(mine)
			.build();
	}
}

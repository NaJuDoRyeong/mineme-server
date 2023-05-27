package com.mineme.server.entity;

import com.mineme.server.common.Utils;
import com.mineme.server.common.enums.ErrorCode;
import com.mineme.server.common.exception.CustomException;
import com.mineme.server.entity.enums.Provider;
import com.mineme.server.entity.enums.UserState;
import com.mineme.server.user.dto.UserInfos;
import com.mineme.server.user.enums.NoticeType;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user")
public class User extends BaseEntity implements UserDetails {

	@Id
	@Column(name = "USER_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COUPLE_ID")
	private Couple coupleId;

	@OneToMany(mappedBy = "userId")
	private List<Post> posts = new ArrayList<>();

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "userId")
	private UserMatchingCode userCode;

	/**
	 * @Todo Apple의 경우 username이 32 byte 길이를 초과함. ERD의 변경이 필요.
	 */
	// 소셜 로그인 ID
	@Column(name = "USERNAME")
	@Size(max = 255)
	@NotNull
	private String username;

	@Column(name = "NICKNAME")
	@Size(max = 64)
	@NotNull
	private String nickname;

	@Enumerated(EnumType.STRING)
	@Column(name = "USER_STATE")
	@NotNull
	private UserState userState;

	@Enumerated(EnumType.STRING)
	@Column(name = "PROVIDER")
	@NotNull
	private Provider provider;

	@Column(name = "PROFILE_IMAGE_URL")
	private String profileImageUrl;

	@Column(name = "EMAIL")
	@Size(max = 64)
	private String email;

	@Column(name = "LAST_LOGIN")
	@NotNull
	private LocalDateTime lastLogin;

	@Column(name = "BIRTHDAY")
	private LocalDate birthday;

	@Column(name = "COMMENT")
	private String comment;

	/* @Todo 해당 필드는 추후 삭제 예정 */
	@Column(name = "GENDER", length = 1)
	private Character gender;

	@Column(name = "INSTA_ID", length = 128)
	private String instaId;

	/**
	 * @Todo 이후 @Size, @NotNull로 validation 예정
	 **/
	@Column(name = "DEVICE_TOKEN", length = 128)
	private String deviceToken;

	/**
	 * @Todo 이후 @Size, @NotNull로 validation 예정
	 */
	@Column(name = "DEVICE", length = 128)
	private String device;

	@Column(name = "PHONE_NUMBER")
	@Size(max = 16)
	private String phoneNumber;

	@Column(name = "EXTRA_VALUES", columnDefinition = "TEXT")
	private String extraValues;

	@Column(name = "NOTICE_FEED")
	@NotNull
	private Boolean noticeFeed;

	@Column(name = "NOTICE_ANNIVERSARY")
	@NotNull
	private Boolean noticeAnniversary;

	@Column(name = "NOTICE_MARKETING")
	@NotNull
	private Boolean noticeMarketing;

	@Builder(builderClassName = "userRegisterBuilder", builderMethodName = "userRegisterBuilder")
	public User(UserMatchingCode userCode, String username, String nickname, UserState userState, Provider provider) {
		this.userCode = userCode;
		this.username = username;
		this.nickname = nickname;
		this.userState = userState;
		this.provider = provider;
		/* @Todo - gender 필드 삭제에 따라 함께 조정할 것 */
		this.gender = 'n';
		this.lastLogin = LocalDateTime.now();
		this.noticeFeed = false;
		this.noticeAnniversary = false;
		this.noticeMarketing = false;
	}

	@Builder(builderClassName = "userInitializeBuilder", builderMethodName = "userInitializeBuilder")
	public User(User user, UserInfos.Init init) {
		/* 변경되지 않는 값 */
		this.id = user.getId();
		this.userCode = user.getUserCode();
		this.username = user.getUsername();
		this.userState = user.getUserState();
		this.provider = user.getProvider();
		this.noticeFeed = false;
		this.noticeAnniversary = false;
		this.noticeMarketing = false;

		/* @Todo - gender 필드 삭제에 따라 함께 조정할 것 */
		this.gender = 'N';

		/* 변경되는 값 */
		this.nickname = init.getNickname();
		this.lastLogin = LocalDateTime.now();
		this.birthday = LocalDate.parse(init.getBirthday());
	}

	public void matchCouple(Couple couple) {
		this.coupleId = couple;
		couple.getUsers().add(this);
	}

	/**
	 * 사용자의 알림 상태를 변경함.
	 */
	public void updateUserNoticeState(UserInfos.Notice type) throws NullPointerException {
		if (type.equals(NoticeType.ANNIVERSARY.getType())) {
			this.noticeAnniversary = type.isAllow();
		} else if (type.equals(NoticeType.FEED.getType())) {
			this.noticeFeed = type.isAllow();
		} else if (type.equals(NoticeType.MARKETING.getType())) {
			this.noticeMarketing = type.isAllow();
		}

		throw new CustomException(ErrorCode.INVALID_REQUEST);
	}

	/** @Todo 커플에서 구현할 것
	 * 커플 프로필의 변경 대상인 필드를 수정함
	 * @return Couple
	 */
	public Couple updateUserProfile(Optional<UserInfos.Modifying> dto) {
		/* 개인 */
		this.nickname = dto.map(UserInfos.Modifying::getNickname).orElse(this.nickname);
		this.comment = dto.map(UserInfos.Modifying::getMineDescription).orElse(this.comment);
		this.instaId = dto.map(UserInfos.Modifying::getInstaId).orElse(this.instaId);
		this.birthday = dto.map(UserInfos.Modifying::getBirthday).orElse(this.birthday);

		if (this.coupleId == null)
			return null;

		/* 커플 */
		return this.getCoupleId().updateCoupleProfile(this, dto);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	@Override
	public String getPassword() {
		return null;
	}

	@Override
	public boolean isAccountNonExpired() {
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return false;
	}

	@Override
	public boolean isEnabled() {
		return false;
	}
}

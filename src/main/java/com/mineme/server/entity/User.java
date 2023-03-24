package com.mineme.server.entity;

import com.mineme.server.entity.enums.Provider;
import com.mineme.server.entity.enums.UserState;
import com.mineme.server.auth.dto.Auth;

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

@Getter
@Entity
@NoArgsConstructor
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

	public static User toPendingUserEntity(String username, Auth.SignRequest dto) {
		return User.builder()
			.userCode(null)
			.username(username)
			.nickname(dto.getUsername())
			.provider(Provider.of(dto.getProviderType()))
			.userState(UserState.PENDING)
			.noticeFeed(false)
			.noticeAnniversary(false)
			.noticeMarketing(false)
			.build();
	}

	@Builder
	private User(UserMatchingCode userCode, String username, String nickname, UserState userState, Provider provider,
		Boolean noticeFeed, Boolean noticeAnniversary, Boolean noticeMarketing) {
		this.userCode = userCode;
		this.username = username;
		this.nickname = nickname;
		this.userState = userState;
		this.provider = provider;
		this.gender = 'n';
		this.lastLogin = LocalDateTime.now();
		this.noticeFeed = noticeFeed;
		this.noticeAnniversary = noticeAnniversary;
		this.noticeMarketing = noticeMarketing;
	}

	public User(Couple coupleId, UserMatchingCode userCode, String username, String nickname, UserState userState,
		Provider provider, String profileImageUrl, String email, LocalDateTime lastLogin, LocalDate birthday,
		String comment, Character gender, String instaId, String deviceToken, String device, String phoneNumber,
		String extraValues, Boolean noticeFeed, Boolean noticeAnniversary, Boolean noticeMarketing) {
		this.coupleId = coupleId;
		this.userCode = userCode;
		this.username = username;
		this.nickname = nickname;
		this.userState = userState;
		this.provider = provider;
		this.profileImageUrl = profileImageUrl;
		this.email = email;
		this.lastLogin = lastLogin;
		this.birthday = birthday;
		this.comment = comment;
		this.gender = gender;
		this.instaId = instaId;
		this.deviceToken = deviceToken;
		this.device = device;
		this.phoneNumber = phoneNumber;
		this.extraValues = extraValues;
		this.noticeFeed = noticeFeed;
		this.noticeAnniversary = noticeAnniversary;
		this.noticeMarketing = noticeMarketing;
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

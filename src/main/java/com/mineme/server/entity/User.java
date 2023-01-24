package com.mineme.server.entity;


import com.mineme.server.entity.enums.Provider;
import com.mineme.server.entity.enums.UserState;
import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.Size;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Entity
public class User extends BaseEntity{

    @Id
    @Column(name = "USER_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "COUPLE_ID")
    private Couple coupleId;

    @Column(name = "USER_CODE")
    @Size(min = 8, max = 8)
    private String userCode;

    @Column(name = "USERNAME")
    @Size(max = 32)
    private String username;

    @Column(name = "NICKNAME")
    @Size(max = 64)
    private String nickname;

    @Enumerated(EnumType.STRING)
    @Column(name = "USER_STATE")
    private UserState userState;

    @Enumerated(EnumType.STRING)
    @Column(name = "PROVIDER")
    private Provider provider;

    /* EMAIL 길이 조정 필요 */
    @Column(name = "PROFILE_IMAGE_URL")
    private String profileImageUrl;

    @Column(name = "EMAIL")
    @Size(max = 64)
    private String email;

    @Column(name = "LAST_LOGIN")
    private LocalDateTime lastLogin;

    @Column(name = "BIRTHDAY")
    private LocalDate birthday;

    @Column(name = "COMMENT")
    private String comment;

    /* ENUM */
    @Column(name = "GENDER", length = 1)
    private Character gender;

    @Column(name = "INSTA_ID")
    @Size(max = 128)
    private String instaId;

    @Column(name = "DEVICE_TOKEN")
    @Size(max = 128)
    private String deviceToken;

    @Column(name = "DEVICE")
    @Size(max = 128)
    private String device;

    @Column(name = "PHONE_NUMBER")
    @Size(max = 16)
    private String phoneNumber;

    @Column(name = "EXTRA_VALUES", columnDefinition = "TEXT")
    private String extraValues;

    @Column(name = "NOTICE_FEED")
    private Boolean noticeFeed;

    @Column(name = "NOTICE_ANNIVERSARY")
    private Boolean noticeAnniversary;

    @Column(name = "NOTICE_MARKETING")
    private Boolean noticeMarketing;
}

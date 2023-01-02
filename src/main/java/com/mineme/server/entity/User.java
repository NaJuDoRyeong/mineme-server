package com.mineme.server.entity;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@RequiredArgsConstructor
public class User {

    @Id
    @Column(name = "USER_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User userId;

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;

    @Column(name = "MODIFIED_AT")
    private LocalDateTime modifiedAt;

    @Column(name = "USER_CODE")
    @Size(max = 8)
    private String userCode;

    @Column(name = "USERNAME")
    @Size(max = 32)
    private String username;

    @Column(name = "NICKNAME")
    @Size(max = 64)
    private String nickname;

    /* ENUM을 통해 저장 */
    @Column(name = "USER_STATE")
    @Size(max = 8)
    private String userState;

    @Column(name = "PROVIDER")
    @Size(max = 8)
    private String provider;

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

    @Column(name = "GENDER", length = 1)
    private Character gender;

    @Column(name = "INSTA_ID")
    @Size(max = 128)
    private String instaId;

    @Column(name = "DEVICE_PUSH_TOKEN")
    @Size(max = 128)
    private String deviceToken;

    @Column(name = "DEVICE")
    @Size(max = 128)
    private String device;
}
